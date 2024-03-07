package com.example.exchangerates2024.domain.use_cases

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.exchangerates2024.App
import com.example.exchangerates2024.R
import com.example.exchangerates2024.data.entities.RatesDataInfoEntity
import com.example.exchangerates2024.domain.entities.CurrencyRateEntity
import com.example.exchangerates2024.domain.repositories.ExchangeRateInfoRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyRatesUseCases {
    @Inject
    lateinit var exchangeRateRepository: ExchangeRateInfoRepository

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val currencyRatesSLE = MutableLiveData<List<CurrencyRateEntity>>()

    init {
        App().component.inject(this)
    }

    fun updateCurrencyRates(context: Context) {
//       NOTE: Для создания запроса с повторением
        val disposable = Observable.interval(0, 30, TimeUnit.SECONDS)
            .flatMapSingle { exchangeRateRepository.getRatesInfo() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                currencyRatesSLE.value = prepareCurrencyRatesData(it, context)
            }, {
            })
        compositeDisposable.add(disposable)
    }

    private fun prepareCurrencyRatesData(
        ratesDataInfo: RatesDataInfoEntity,
        context: Context
    ): List<CurrencyRateEntity> {
        val rates = mutableListOf<CurrencyRateEntity>()
        rates.add(
            CurrencyRateEntity(
                context.getString(R.string.usd),
                context.getString(R.string.sign_usd),
                ratesDataInfo.rates.USD
            )
        )
        rates.add(
            CurrencyRateEntity(
                context.getString(R.string.eur),
                context.getString(R.string.sign_eur),
                ratesDataInfo.rates.EUR
            )
        )
        rates.add(
            CurrencyRateEntity(
                context.getString(R.string.gbp),
                context.getString(R.string.sign_gbp),
                ratesDataInfo.rates.GBP
            )
        )

        return rates
    }

    fun dispose() {
        compositeDisposable.dispose()
    }

}