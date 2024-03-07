package com.example.exchangerates2024.presentation.adapters

import android.content.Context
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
//    NOTE: Контекст приложения не меняется на протяжении всего жизненного цикла приложения
    private val appContext: Context,
    val changeInputCurrencyListener: (inputCurrency: CurrencyRateEntity) -> Unit,
    val inputNumberChangeListener: (inputNumber: String, outputNumber: String) -> Unit
) :
    RecyclerView.Adapter<ExchangeRateAdapter.ExchangeRateViewHolder>() {
    @Inject
    lateinit var userAccountsUseCases: UserAccountsUseCases

    private val DEFAULT_INPUT_VALUE = "0.00"

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

    var lastCurrency: CurrencyRateEntity? = null

    var inputNumber = DEFAULT_INPUT_VALUE
        set(value) {
            if (field == value) return
            field = value
            if (value != formatDouble(
                    userAccountsUseCases.convertStringToAnotherCurrencyValue(
                        outputNumber.toDoubleOrNull() ?: 0.0,
                        outputCurrency!!,
                        inputCurrency!!
                    ), 2
                )
            ) {
                outputNumber = formatDouble(
                    userAccountsUseCases.convertStringToAnotherCurrencyValue(
                        value.toDoubleOrNull() ?: 0.0,
                        inputCurrency!!,
                        outputCurrency!!
                    ), 2
                )
                inputNumberChangeListener(value, outputNumber)
            }
        }
    var outputNumber = DEFAULT_INPUT_VALUE

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
//                todo пусть этим занимаются другие функции а еще лучше классы
//                todo сделать проверку на ноль и убрать !!

                tvRate.text =
                    appContext.getString(
                        R.string.currency_exchange_rate, inputCurrency?.sign, formatDouble(
                            outputCurrency!!.value / inputCurrency!!.value,
                            2
                        ), outputCurrency!!.sign
                    )
                if (lastCurrency != null && inputCurrency != lastCurrency) {
                    etCurrencyValue.setText(DEFAULT_INPUT_VALUE)
                } else if (inputNumber != etCurrencyValue.text.toString())
                    etCurrencyValue.setText(inputNumber)


                lastCurrency = inputCurrency

                val userAccountValue =
                    userAccountsUseCases.getAccountValueByCurrency(appContext, inputCurrency!!.name)
                tvAccount.text = appContext.getString(
                    R.string.you_have,
                    formatDouble(userAccountValue, 2),
                    inputCurrency!!.sign
                )

            }

//            todo позапихивать все в минифункции

            etCurrencyValue.clearTextChangedListeners()
            etCurrencyValue.addTextChangedListener {
                if (countDecimalPlaces(it.toString()) > 2) etCurrencyValue.setText(inputNumber)
                else inputNumber = it.toString()
            }
        }
    }

    //    todo перенести в класс для работы с цифрами
    private fun countDecimalPlaces(input: String): Int {
        val decimalIndex = input.indexOf('.')
        return if (decimalIndex != -1) {
            input.length - decimalIndex - 1
        } else {
            0
        }
    }

    // todo возможно изменить слова
    inner class ExchangeRateViewHolder(itemView: RateItemBinding) : ViewHolder(itemView.root) {
        val tvCurrency = itemView.tvCurrency
        val etCurrencyValue = itemView.etCurrencyValue
        val tvAccount = itemView.tvAccount
        val tvRate = itemView.tvRate
    }

    fun formatDouble(number: Double, decimalCount: Int): String {
        return String.format("%.${decimalCount}f", number).replace(',', '.')
    }

}