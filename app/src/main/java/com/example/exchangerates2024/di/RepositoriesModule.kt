package com.example.exchangerates2024.di

import com.example.exchangerates2024.data.repositories.ExchangeRateInfoRepositoryImpl
import com.example.exchangerates2024.domain.repositories.ExchangeRateInfoRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {
   // todo
    @Provides
    @Singleton
    fun provideExchangeRateInfoRepository(): ExchangeRateInfoRepository {
        return ExchangeRateInfoRepositoryImpl()
    }
}