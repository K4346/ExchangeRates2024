package com.example.exchangerates2024.di

import com.example.exchangerates2024.domain.use_cases.CurrencyRatesUseCases
import com.example.exchangerates2024.domain.use_cases.CurrencyRatesUseCasesImpl
import com.example.exchangerates2024.domain.use_cases.CurrencyValueEditUseCases
import com.example.exchangerates2024.domain.use_cases.CurrencyValueEditUseCasesImpl
import com.example.exchangerates2024.domain.use_cases.UserAccountsUseCases
import com.example.exchangerates2024.domain.use_cases.UserAccountsUseCasesImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCasesModule {
    @Provides
    @Singleton
    fun provideCurrencyRatesUseCases(): CurrencyRatesUseCases {
        return CurrencyRatesUseCasesImpl()
    }

    @Provides
    @Singleton
    fun provideUserAccountUseCases(): UserAccountsUseCases {
        return UserAccountsUseCasesImpl()
    }

    @Provides
    @Singleton
    fun provideCurrencyValueEditUseCases(): CurrencyValueEditUseCases {
        return CurrencyValueEditUseCasesImpl()
    }
}