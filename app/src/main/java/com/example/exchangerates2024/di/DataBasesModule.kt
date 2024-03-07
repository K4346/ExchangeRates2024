package com.example.exchangerates2024.di

import com.example.exchangerates2024.data.db.UserAccountsInfoDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBasesModule {
    @Provides
    @Singleton
    fun provideUserAccountsInfoDataBase(): UserAccountsInfoDataBase {
        return UserAccountsInfoDataBase()
    }
}