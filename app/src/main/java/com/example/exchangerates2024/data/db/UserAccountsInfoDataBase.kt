package com.example.exchangerates2024.data.db

import android.content.Context
import android.content.SharedPreferences
import com.example.exchangerates2024.App
import com.example.exchangerates2024.R

// NOTE: В задании ничего не сказано про работу с бд (например рум), поэтому для счетов я решил использовать обычный жесткозакодированный sharedPreferences
class UserAccountsInfoDataBase {

//    todo хорошей практикой является достать значения ШП 1 раз и менять при изменении (так как чтение из бд обходится дороже), в данном случае решил оставить как есть
    fun getUserCurrencyAccount(context: Context,currencyName: String): Float {
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(EXCHANGE_RATES_STORAGE, Context.MODE_PRIVATE)
    if (sharedPreferences.all.entries.isEmpty()) {
        sharedPreferences.edit().putFloat(context.getString(R.string.usd), 100f).apply()
        sharedPreferences.edit().putFloat(context.getString(R.string.eur), 0f).apply()
        sharedPreferences.edit().putFloat(context.getString(R.string.gbp), 0f).apply()
    }
        return sharedPreferences.getFloat(currencyName, 0.0f)
    }

    fun setUserCurrencyAccountValue(context: Context,currencyName: String,currencyValue:Float){
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(EXCHANGE_RATES_STORAGE, Context.MODE_PRIVATE)
        sharedPreferences.edit().putFloat(currencyName, currencyValue).apply()
    }

    companion object {
        private const val EXCHANGE_RATES_STORAGE = "EXCHANGE_RATES_STORAGE"
//        private const val EXCHANGE_RATES_STORAGE = "EXCHANGE_RATES_STORAGE"
    }
}