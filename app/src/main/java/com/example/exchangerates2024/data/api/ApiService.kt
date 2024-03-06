package com.example.exchangerates2024.data.api

import com.example.exchangerates2024.data.entities.RatesDataInfoEntity
import io.reactivex.Single
import retrofit2.http.GET


interface ApiService {
    @GET(value = "latest.js")
    fun getRatesInfo(
    ): Single<RatesDataInfoEntity>


}