package com.example.exchangerates2024.data.entities


data class RatesDataInfoEntity(
    val base: String,
    val date: String,
    val rates: RatesData,
    val timestamp: Int
)

data class RatesData(
    val EUR: Double,
    val GBP: Double,
    val USD: Double,
)