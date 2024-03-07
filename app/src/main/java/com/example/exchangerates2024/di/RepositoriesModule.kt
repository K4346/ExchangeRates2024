package com.example.exchangerates2024.di

import com.example.exchangerates2024.data.repositories.ExchangeRateInfoRepositoryImpl
import com.example.exchangerates2024.data.repositories.UserAccountsRepositoryImpl
import com.example.exchangerates2024.domain.repositories.ExchangeRateInfoRepository
import com.example.exchangerates2024.domain.repositories.UserAccountsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {
    @Provides
    @Singleton
    fun provideExchangeRateInfoRepository(): ExchangeRateInfoRepository {
        return ExchangeRateInfoRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideUserAccountsRepository(): UserAccountsRepository {
        return UserAccountsRepositoryImpl()
    }
}