package com.example.exchangerates2024.data.db

import android.content.Context
import android.content.SharedPreferences
import com.example.exchangerates2024.R

// NOTE: В задании ничего не сказано про работу с бд (например рум), поэтому для счетов я решил использовать обычный жесткозакодированный sharedPreferences
class UserAccountsInfoDataBase {

    //    NOTE: хорошей практикой является достать значения SP 1 раз и менять при изменении (так как чтение из бд обходится дороже), в связи с тем что в задании не было описано конкретики по сохранению данных, решил не усложнять
    fun getUserCurrencyAccount(context: Context, currencyName: String): Float {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(EXCHANGE_RATES_STORAGE, Context.MODE_PRIVATE)
        if (sharedPreferences.all.entries.isEmpty()) {
            sharedPreferences.edit().putFloat(context.getString(R.string.usd), 100f).apply()
            sharedPreferences.edit().putFloat(context.getString(R.string.eur), 0f).apply()
            sharedPreferences.edit().putFloat(context.getString(R.string.gbp), 0f).apply()
        }
        return sharedPreferences.getFloat(currencyName, 0.0f)
    }

    fun setUserCurrencyAccountValue(context: Context, currencyName: String, currencyValue: Float) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(EXCHANGE_RATES_STORAGE, Context.MODE_PRIVATE)
        sharedPreferences.edit().putFloat(currencyName, currencyValue).apply()
    }

    companion object {
        private const val EXCHANGE_RATES_STORAGE = "EXCHANGE_RATES_STORAGE"
    }

}