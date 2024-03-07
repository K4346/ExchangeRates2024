package com.example.exchangerates2024.domain.repositories

import android.content.Context

interface UserAccountsRepository {
    fun getUserAccount(context: Context, currencyName: String):Float
    fun setUserAccountValue(context: Context, currencyName: String, currencyValue:Float)
}