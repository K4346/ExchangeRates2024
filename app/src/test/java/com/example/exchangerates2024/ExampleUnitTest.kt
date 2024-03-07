package com.example.exchangerates2024

import com.example.exchangerates2024.domain.use_cases.CurrencyValueEditUseCases
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    private val currencyValueEditUseCases = CurrencyValueEditUseCases()
    @Test
    fun currencyValueEditUseCases_isCorrect() {
//        countDecimalPlaces
        val test1Value = currencyValueEditUseCases.countDecimalPlaces("666")
        val test2Value = currencyValueEditUseCases.countDecimalPlaces("555.9")
        val test3Value = currencyValueEditUseCases.countDecimalPlaces("0.00")
        assertEquals(test1Value, 0)
        assertEquals(test2Value, 1)
        assertEquals(test3Value, 2)

        //        formatDouble
        val test1FormatDouble = currencyValueEditUseCases.formatDouble(666.0, 2)
        val test2FormatDouble = currencyValueEditUseCases.formatDouble(2.2323542342, 3)
        val test3FormatDouble = currencyValueEditUseCases.formatDouble(0.00, 0)
        assertEquals(test1FormatDouble, "666.00")
        assertEquals(test2FormatDouble, "2.232")
        assertEquals(test3FormatDouble, "0")
    }
}