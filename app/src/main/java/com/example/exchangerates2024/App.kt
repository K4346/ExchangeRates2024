package com.example.exchangerates2024

import android.app.Application
import com.example.exchangerates2024.di.AppComponent
import com.example.exchangerates2024.di.DaggerAppComponent

class App : Application() {
    val component: AppComponent by lazy { DaggerAppComponent.create() }
}