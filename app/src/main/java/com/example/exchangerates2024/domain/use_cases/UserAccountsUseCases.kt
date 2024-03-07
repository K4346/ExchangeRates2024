package com.example.exchangerates2024.domain.use_cases

import android.content.Context
import com.example.exchangerates2024.App
import com.example.exchangerates2024.domain.entities.CurrencyRateEntity
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

    fun setAccountValueByCurrency(context: Context, currencyName: String, currencyValue: Double) {
        return userAccountsRepository.setUserAccountValue(
            context,
            currencyName,
            currencyValue.toFloat()
        )
    }

    fun convertStringToAnotherCurrencyValue(
        inputNumber: Double,
        inputCurrency: CurrencyRateEntity,
        outputCurrency: CurrencyRateEntity
    ): Double {
        return inputNumber * outputCurrency.value / inputCurrency.value
    }

    //    NOTE: Вывожу все доступные счета (с которыми можно провести операции)
    fun getAvailableAccountsInfo(
        context: Context,
        availableCurrencyRates: List<CurrencyRateEntity>?
    ): String {
        val availableAccountsInfo = StringBuilder()
        availableCurrencyRates?.forEachIndexed { index, it ->
            val accountValue = getAccountValueByCurrency(context, it.name)
            availableAccountsInfo.append(
                "${it.name}: ${it.sign}${formatDouble(accountValue, 2)}"
            )
            if (index != availableCurrencyRates.lastIndex) {
                availableAccountsInfo.append("\n")
            }
        }
        return availableAccountsInfo.toString()
    }

    //    todo
    fun formatDouble(number: Double, decimalCount: Int): String {
        return String.format("%.${decimalCount}f", number).replace(',', '.')
    }

}