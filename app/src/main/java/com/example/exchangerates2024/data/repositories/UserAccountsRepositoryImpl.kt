package com.example.exchangerates2024.data.repositories

import android.content.Context
import com.example.exchangerates2024.App
import com.example.exchangerates2024.data.db.UserAccountsInfoDataBase
import com.example.exchangerates2024.domain.repositories.UserAccountsRepository
import javax.inject.Inject

class UserAccountsRepositoryImpl : UserAccountsRepository {
    @Inject
    lateinit var userAccountsInfoDB: UserAccountsInfoDataBase

    init {
        App().component.inject(this)
    }

    override fun getUserAccount(context: Context, currencyName: String): Float {
        return userAccountsInfoDB.getUserCurrencyAccount(context, currencyName)
    }

    override fun setUserAccountValue(context: Context, currencyName: String, currencyValue: Float) {
        return userAccountsInfoDB.setUserCurrencyAccountValue(context, currencyName, currencyValue)
    }
}