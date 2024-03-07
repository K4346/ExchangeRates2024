package com.example.exchangerates2024.presentation.currency_exchange_screen

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.exchangerates2024.R
import com.example.exchangerates2024.databinding.FragmentCurrencyExchangeBinding
import com.example.exchangerates2024.presentation.adapters.ExchangeRateAdapter

class CurrencyExchangeFragment : Fragment() {

    // NOTE: Для данной задачи достаточно активити, но так как об этом ничего не сказано, решил добавить (для теоретического будущего расширения)
    private var _binding: FragmentCurrencyExchangeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrencyExchangeViewModel by viewModels()


    private var currencyExchangeInputAdapter: ExchangeRateAdapter? = null
    private var currencyExchangeOutputAdapter: ExchangeRateAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrencyExchangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
        initObservers()
        initAdapters()
    }

    private fun initAdapters() {
//        NOTE: Не самый оптимальный способ обновления данных, достаточно лишь менять всего 2 элемента при скролле, но в контексте данной задачи решил оставить как есть
//       todo возможно стоит передавать еще и их положения чтоб не обновлять все
        currencyExchangeInputAdapter =
            ExchangeRateAdapter(requireContext().applicationContext, { currentCurrency ->
                currencyExchangeOutputAdapter?.outputCurrency = currentCurrency
                requireActivity().currentFocus?.clearFocus()
                binding.rvExchangeRateInput.postDelayed({
                    currencyExchangeInputAdapter?.notifyDataSetChanged()
                    currencyExchangeOutputAdapter?.notifyDataSetChanged()
                }, 300)
            }) { inputNumber, outputNumber ->
                currencyExchangeOutputAdapter?.outputNumber = inputNumber
                currencyExchangeOutputAdapter?.inputNumber = outputNumber
                currencyExchangeOutputAdapter?.notifyDataSetChanged()
            }
        binding.rvExchangeRateInput.adapter = currencyExchangeInputAdapter
        PagerSnapHelper().attachToRecyclerView(binding.rvExchangeRateInput)

        currencyExchangeOutputAdapter =
            ExchangeRateAdapter(requireContext().applicationContext, { currentCurrency ->
                currencyExchangeInputAdapter?.outputCurrency = currentCurrency
                requireActivity().currentFocus?.clearFocus()
                binding.rvExchangeRateOutput.postDelayed({
                    currencyExchangeInputAdapter?.notifyDataSetChanged()
                    currencyExchangeOutputAdapter?.notifyDataSetChanged()
                }, 300)
            }) { inputNumber, outputNumber ->
                currencyExchangeInputAdapter?.outputNumber = inputNumber
                currencyExchangeInputAdapter?.inputNumber = outputNumber
                currencyExchangeInputAdapter?.notifyDataSetChanged()
            }
        binding.rvExchangeRateOutput.adapter = currencyExchangeOutputAdapter
        PagerSnapHelper().attachToRecyclerView(binding.rvExchangeRateOutput)
    }

    private fun initObservers() {
        viewModel.currencyRatesMLE.observe(viewLifecycleOwner) {

            currencyExchangeInputAdapter?.rates = it
            currencyExchangeOutputAdapter?.rates = it
        }
        viewModel.showDialogSLE.observe(viewLifecycleOwner) { dialogMessage ->
            showAlertDialog(requireContext(), getString(R.string.accounts_info), dialogMessage)
        }
    }

    private fun initListeners() {
        binding.buttonExchangeCurrencies.setOnClickListener {
            val inputCurrency = currencyExchangeInputAdapter?.inputCurrency
            val outputCurrency = currencyExchangeInputAdapter?.outputCurrency
//            todo переделать
            val inputNumber = currencyExchangeInputAdapter?.inputNumber?.toDoubleOrNull()
            Log.i("kpop", currencyExchangeInputAdapter?.inputNumber.toString())
            if (inputCurrency == null || outputCurrency == null || inputNumber == null) return@setOnClickListener

            viewModel.prepareDialogInfo(inputCurrency, outputCurrency, inputNumber)
        }
    }

    private fun showAlertDialog(context: Context, title: String, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle(title)

        alertDialogBuilder.setMessage(message)

        alertDialogBuilder.setPositiveButton("Ok") { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}