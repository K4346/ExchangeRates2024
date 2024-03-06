package com.example.exchangerates2024.presentation.currency_exchange_screen

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.exchangerates2024.App
import com.example.exchangerates2024.SingleLiveEvent
import com.example.exchangerates2024.domain.entities.CurrencyRateEntity
import com.example.exchangerates2024.domain.use_cases.CurrencyRatesUseCases
import javax.inject.Inject

class CurrencyExchangeViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var currencyRatesUseCases: CurrencyRatesUseCases

    val currencyRatesSLE: SingleLiveEvent<List<CurrencyRateEntity>>

    init {
        (application as App).component.inject(this)

        currencyRatesSLE = currencyRatesUseCases.currencyRatesSLE

        currencyRatesUseCases.updateCurrencyRates(context = application.applicationContext)
    }

    override fun onCleared() {
        super.onCleared()
            currencyRatesUseCases.dispose()
    }
}