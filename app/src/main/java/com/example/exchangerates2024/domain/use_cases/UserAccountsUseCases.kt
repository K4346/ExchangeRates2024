package com.example.exchangerates2024.domain.use_cases

import android.content.Context
import com.example.exchangerates2024.App
import com.example.exchangerates2024.domain.entities.CurrencyRateEntity
import com.example.exchangerates2024.domain.repositories.UserAccountsRepository
import javax.inject.Inject

class UserAccountsUseCasesImpl : UserAccountsUseCases {
    @Inject
    lateinit var userAccountsRepository: UserAccountsRepository

    @Inject
    lateinit var editUseCase: CurrencyValueEditUseCases

    init {
        App().component.inject(this)
    }

    override fun getAccountValueByCurrency(context: Context, currencyName: String): Double {
        return userAccountsRepository.getUserAccount(context, currencyName).toDouble()
    }

    override fun setAccountValueByCurrency(
        context: Context,
        currencyName: String,
        currencyValue: Double
    ) {
        return userAccountsRepository.setUserAccountValue(
            context,
            currencyName,
            currencyValue.toFloat()
        )
    }

    override fun convertStringToAnotherCurrencyValue(
        inputNumber: Double,
        inputCurrency: CurrencyRateEntity,
        outputCurrency: CurrencyRateEntity
    ): Double {
        return inputNumber * outputCurrency.value / inputCurrency.value
    }

    //    NOTE: Вывожу все доступные счета (с которыми можно провести операции)
    override fun getAvailableAccountsInfo(
        context: Context,
        availableCurrencyRates: List<CurrencyRateEntity>?
    ): String {
        val availableAccountsInfo = StringBuilder()
        availableCurrencyRates?.forEachIndexed { index, it ->
            val accountValue = getAccountValueByCurrency(context, it.name)
            availableAccountsInfo.append(
                "${it.name}: ${it.sign}${editUseCase.formatDouble(accountValue, 2)}"
            )
            if (index != availableCurrencyRates.lastIndex) {
                availableAccountsInfo.append("\n")
            }
        }
        return availableAccountsInfo.toString()
    }

}

interface UserAccountsUseCases {


    fun getAccountValueByCurrency(context: Context, currencyName: String): Double

    fun setAccountValueByCurrency(context: Context, currencyName: String, currencyValue: Double)

    fun convertStringToAnotherCurrencyValue(
        inputNumber: Double,
        inputCurrency: CurrencyRateEntity,
        outputCurrency: CurrencyRateEntity
    ): Double

    //    NOTE: Вывожу все доступные счета (с которыми можно провести операции)
    fun getAvailableAccountsInfo(
        context: Context,
        availableCurrencyRates: List<CurrencyRateEntity>?
    ): String
}