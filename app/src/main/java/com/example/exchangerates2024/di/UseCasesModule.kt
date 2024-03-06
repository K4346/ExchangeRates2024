package com.example.exchangerates2024.di

import com.example.exchangerates2024.data.repositories.ExchangeRateInfoRepositoryImpl
import com.example.exchangerates2024.domain.use_cases.CurrencyRatesUseCases
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//todo также сделать пару юнит тестов
@Module
class UseCasesModule {
    @Provides
    @Singleton
    fun provideCurrencyRatesUseCases(): CurrencyRatesUseCases {
        return CurrencyRatesUseCases()
    }
}