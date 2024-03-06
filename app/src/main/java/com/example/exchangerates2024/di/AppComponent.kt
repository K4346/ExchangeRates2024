package com.example.exchangerates2024.di

import com.example.exchangerates2024.data.repositories.ExchangeRateInfoRepositoryImpl
import com.example.exchangerates2024.domain.use_cases.CurrencyRatesUseCases
import com.example.exchangerates2024.presentation.currency_exchange_screen.CurrencyExchangeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiFactoryModule::class, ApiServiceModule::class, RepositoriesModule::class, UseCasesModule::class])
interface AppComponent {

    fun inject(repository: ExchangeRateInfoRepositoryImpl)
    fun inject(viewModel: CurrencyExchangeViewModel)
    fun inject(useCases: CurrencyRatesUseCases)
}