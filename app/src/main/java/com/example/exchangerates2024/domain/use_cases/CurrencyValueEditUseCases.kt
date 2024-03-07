package com.example.exchangerates2024.domain.use_cases

//NOTE: класс для работы с валютными числами
interface CurrencyValueEditUseCases {
    fun countDecimalPlaces(input: String): Int
    fun formatDouble(number: Double, decimalCount: Int): String
}

class CurrencyValueEditUseCasesImpl : CurrencyValueEditUseCases {
    override fun countDecimalPlaces(input: String): Int {
        val decimalIndex = input.indexOf('.')
        return if (decimalIndex != -1) {
            input.length - decimalIndex - 1
        } else {
            0
        }
    }

    override fun formatDouble(number: Double, decimalCount: Int): String {
        return String.format("%.${decimalCount}f", number).replace(',', '.')
    }
}