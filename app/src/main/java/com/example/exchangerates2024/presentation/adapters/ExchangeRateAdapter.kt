package com.example.exchangerates2024.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.exchangerates2024.App
import com.example.exchangerates2024.ExtendedEditText
import com.example.exchangerates2024.R
import com.example.exchangerates2024.databinding.RateItemBinding
import com.example.exchangerates2024.domain.entities.CurrencyRateEntity
import com.example.exchangerates2024.domain.use_cases.CurrencyValueEditUseCases
import com.example.exchangerates2024.domain.use_cases.UserAccountsUseCases
import javax.inject.Inject

class ExchangeRateAdapter(
//    NOTE: Контекст приложения не меняется на протяжении всего жизненного цикла приложения
    private val appContext: Context,
    val changeInputCurrencyListener: (inputCurrency: CurrencyRateEntity) -> Unit,
    val inputNumberChangeListener: (inputNumber: String, outputNumber: String) -> Unit
) :
    RecyclerView.Adapter<ExchangeRateAdapter.ExchangeRateViewHolder>() {
    @Inject
    lateinit var userAccountsUseCases: UserAccountsUseCases

    @Inject
    lateinit var editUseCase: CurrencyValueEditUseCases

    private val defaultCurrencyValue = ""

    init {
        App().component.inject(this)
    }

    var inputCurrency: CurrencyRateEntity? = null
        set(value) {
            if (value != null && field != value) {
                field = value
                changeInputCurrencyListener(value)
            }
        }
    var outputCurrency: CurrencyRateEntity? = null

    private var lastCurrency: CurrencyRateEntity? = null

    var inputNumber = defaultCurrencyValue
        set(value) {
            if (field == value) return
            field = value
            onInputNumberChange(value)

        }

    var outputNumber = defaultCurrencyValue

    var rates: List<CurrencyRateEntity> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeRateViewHolder {
        val binding = RateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ExchangeRateViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return rates.size
    }

    override fun onBindViewHolder(holder: ExchangeRateViewHolder, position: Int) {
        with(holder) {
            inputCurrency = rates[position]
            tvCurrency.text = inputCurrency?.name
            if (outputCurrency != null && inputCurrency != null) {
                makeTvRate(tvRate)
                setEditTextCurrencyValue(etCurrencyValue)
                lastCurrency = inputCurrency
                makeTvAccount(tvAccount)
            }

            initEditTextCurrencyValueListeners(etCurrencyValue)
        }
    }

    private fun initEditTextCurrencyValueListeners(etCurrencyValue: ExtendedEditText) {
        etCurrencyValue.clearTextChangedListeners()
        etCurrencyValue.addTextChangedListener {
            if (it.toString() == inputNumber) return@addTextChangedListener
            if ((editUseCase.countDecimalPlaces(it.toString()) > 2) || it.toString().length > 6) {
                etCurrencyValue.setText(inputNumber)
            } else {
                inputNumber = it.toString()
            }
        }
    }

    private fun makeTvAccount(tvAccount: TextView) {
        val userAccountValue =
            userAccountsUseCases.getAccountValueByCurrency(appContext, inputCurrency!!.name)
        tvAccount.text = appContext.getString(
            R.string.you_have,
            editUseCase.formatDouble(userAccountValue, 2),
            inputCurrency!!.sign
        )
    }

    private fun setEditTextCurrencyValue(etCurrencyValue: ExtendedEditText) {
        if (lastCurrency != null && inputCurrency != lastCurrency) {
            etCurrencyValue.setText(defaultCurrencyValue)
        } else if (inputNumber != etCurrencyValue.text.toString())
            etCurrencyValue.setText(inputNumber)
    }

    private fun makeTvRate(tvRate: TextView) {
        tvRate.text =
            appContext.getString(
                R.string.currency_exchange_rate, inputCurrency?.sign, editUseCase.formatDouble(
                    outputCurrency!!.value / inputCurrency!!.value,
                    2
                ), outputCurrency!!.sign
            )
    }

    private fun onInputNumberChange(input: String) {
        if (input != editUseCase.formatDouble(
                userAccountsUseCases.convertStringToAnotherCurrencyValue(
                    outputNumber.toDoubleOrNull() ?: 0.0,
                    outputCurrency!!,
                    inputCurrency!!
                ), 2
            )
        ) {
            outputNumber = editUseCase.formatDouble(
                userAccountsUseCases.convertStringToAnotherCurrencyValue(
                    input.toDoubleOrNull() ?: 0.0,
                    inputCurrency!!,
                    outputCurrency!!
                ), 2
            )
            inputNumberChangeListener(input, outputNumber)
        }
    }


    inner class ExchangeRateViewHolder(itemView: RateItemBinding) : ViewHolder(itemView.root) {
        val tvCurrency = itemView.tvCurrency
        val etCurrencyValue = itemView.etCurrencyValue
        val tvAccount = itemView.tvAccount
        val tvRate = itemView.tvRate
    }


}