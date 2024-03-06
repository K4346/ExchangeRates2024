package com.example.exchangerates2024.data.repositories

import com.example.exchangerates2024.App
import com.example.exchangerates2024.data.api.ApiService
import com.example.exchangerates2024.data.entities.RatesDataInfoEntity
import com.example.exchangerates2024.domain.repositories.ExchangeRateInfoRepository
import io.reactivex.Single
import javax.inject.Inject

class ExchangeRateInfoRepositoryImpl : ExchangeRateInfoRepository {

    @Inject
    lateinit var apiService: ApiService

    //    todo это норм?
    init {
        App().component.inject(this)
    }

    override fun getRatesInfo(): Single<RatesDataInfoEntity> {
        return apiService.getRatesInfo()
    }
}