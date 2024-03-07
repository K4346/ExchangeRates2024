package com.example.exchangerates2024.presentation.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.exchangerates2024.App
import com.example.exchangerates2024.R
import com.example.exchangerates2024.databinding.RateItemBinding
import com.example.exchangerates2024.domain.entities.CurrencyRateEntity
import com.example.exchangerates2024.domain.use_cases.UserAccountsUseCases
import javax.inject.Inject

class ExchangeRateAdapter(
    private val appContext: Context,
    val changeInputCurrencyListener: (inputCurrency: CurrencyRateEntity) -> Unit,
    val inputNumberChangeListener: (inputNumber: String) -> Unit
) :
    RecyclerView.Adapter<ExchangeRateAdapter.ExchangeRateViewHolder>() {
@Inject
lateinit var userAccountsUseCases: UserAccountsUseCases
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

    var inputNumber = ""

    var inputFlag = false

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
        inputCurrency = rates[position]
        with(holder) {
            tvCurrency.text = inputCurrency?.name
            if (outputCurrency != null && inputCurrency != null) {
//                todo пусть этим занимаются другие функции а еще лучше классы
//                todo сделать проверку на ноль и убрать !!

                tvRate.text =
                    appContext.getString(
                        R.string.currency_exchange_rate, inputCurrency?.sign, formatDouble(
                            outputCurrency!!.value / inputCurrency!!.value,
                            2
                        ), outputCurrency!!.sign
                    )
                if (!inputFlag) {
                    etCurrencyValue.setText(
                        formatDouble(
                            (inputNumber.toDoubleOrNull() ?: 0.0) * outputCurrency!!.value / inputCurrency!!.value, 2
                        )
                    )
                }
                tvAccount.text = userAccountsUseCases.getAccountValueByCurrency(appContext,inputCurrency!!.name).toString()
            }

//            todo позапихивать все в минифункции

            etCurrencyValue.clearTextChangedListeners()
            etCurrencyValue.addTextChangedListener {
                if (etCurrencyValue.isFocusable){
                    Log.i("kpop",etCurrencyValue.isFocusable.toString())
                }
                if (inputFlag) {
                    inputNumberChangeListener(it.toString())
                }
            }
        }
    }

    // todo возможно изменить слова
    inner class ExchangeRateViewHolder(itemView: RateItemBinding) : ViewHolder(itemView.root) {
        val tvCurrency = itemView.tvCurrency
        val etCurrencyValue = itemView.etCurrencyValue
        val tvAccount = itemView.tvAccount
        val tvRate = itemView.tvRate
    }

    data class ExchangeRatesForAdapter(
        val inputCurrency: CurrencyRateEntity,
        val outputCurrency: CurrencyRateEntity
    )

    fun formatDouble(number: Double, decimalCount: Int): String {
        return String.format("%.${decimalCount}f", number).replace(',','.')
    }
}