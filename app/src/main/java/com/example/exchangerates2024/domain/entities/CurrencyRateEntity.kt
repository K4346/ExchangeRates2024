package com.example.exchangerates2024.domain.entities

//todo возможно обработать в случае не получения
//data class CurrencyRatesEntity(
//    val currencies: List<CurrencyRateEntity>
//)

data class CurrencyRateEntity(
    val name: String,
    val sign: String,
    val value: Double
)