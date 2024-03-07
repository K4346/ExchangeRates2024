package com.example.exchangerates2024.domain.repositories

import com.example.exchangerates2024.data.entities.RatesDataInfoEntity
import io.reactivex.Single

interface ExchangeRateInfoRepository {

    fun getRatesInfo(): Single<RatesDataInfoEntity>

}