package com.example.exchangerates2024.domain.use_cases

import android.content.Context
import com.example.exchangerates2024.App
import com.example.exchangerates2024.domain.repositories.UserAccountsRepository
import javax.inject.Inject

class UserAccountsUseCases {
    @Inject
   lateinit var userAccountsRepository: UserAccountsRepository

    init {
        App().component.inject(this)
    }

    fun getAccountValueByCurrency(context: Context, currencyName: String): Double {
        return userAccountsRepository.getUserAccount(context, currencyName).toDouble()
    }
    fun setAccountValueByCurrency(context: Context, currencyName: String,currencyValue:Double) {
        return userAccountsRepository.setUserAccountValue(context, currencyName,currencyValue.toFloat())
    }
}