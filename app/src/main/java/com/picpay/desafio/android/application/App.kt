package com.picpay.desafio.android.application

import android.app.Application
import com.picpay.desafio.android.dependency.apiModule
import com.picpay.desafio.android.dependency.repositoryModule
import com.picpay.desafio.android.dependency.retrofitModule
import com.picpay.desafio.android.dependency.viewModelModule
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(repositoryModule, viewModelModule, retrofitModule, apiModule))
        }
    }
}