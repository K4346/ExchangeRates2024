package com.example.exchangerates2024.presentation.currency_exchange_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.exchangerates2024.App
import com.example.exchangerates2024.R
import com.example.exchangerates2024.SingleLiveEvent
import com.example.exchangerates2024.domain.entities.CurrencyRateEntity
import com.example.exchangerates2024.domain.use_cases.CurrencyRatesUseCases
import com.example.exchangerates2024.domain.use_cases.UserAccountsUseCases
import javax.inject.Inject

class CurrencyExchangeViewModel(private val application: Application) :
    AndroidViewModel(application) {
    @Inject
    lateinit var currencyRatesUseCases: CurrencyRatesUseCases

    @Inject
    lateinit var accountsUseCases: UserAccountsUseCases

    val currencyRatesSLE: SingleLiveEvent<List<CurrencyRateEntity>>

    val showDialogSLE = SingleLiveEvent<String>()

    init {
        (application as App).component.inject(this)

        currencyRatesSLE = currencyRatesUseCases.currencyRatesSLE

        currencyRatesUseCases.updateCurrencyRates(context = application.applicationContext)
    }

    fun prepareDialogInfo(
        inputCurrency: CurrencyRateEntity,
        outputCurrency: CurrencyRateEntity,
        inputNumber: Double
    ) {

        if (inputCurrency.name==outputCurrency.name){
            showDialogSLE.value = application.getString(R.string.one_account_is_selected)
            return
        }

        val userInputAccountValue =
            accountsUseCases.getAccountValueByCurrency(application, inputCurrency.name)

        if (inputNumber > userInputAccountValue) {
            showDialogSLE.value = application.getString(R.string.insufficient_funds_in_the_account)
            return
        }
        val userOutputAccountValue =
            accountsUseCases.getAccountValueByCurrency(application, outputCurrency.name)
        val outputNumber = inputNumber * outputCurrency.value / inputCurrency.value

        accountsUseCases.setAccountValueByCurrency(
            application,
            inputCurrency.name,
            userInputAccountValue - inputNumber
        )
        accountsUseCases.setAccountValueByCurrency(
            application,
            outputCurrency.name,
            userOutputAccountValue + outputNumber
        )
    }

    override fun onCleared() {
        super.onCleared()
        currencyRatesUseCases.dispose()
    }
}