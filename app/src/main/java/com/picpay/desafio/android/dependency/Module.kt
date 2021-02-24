package com.picpay.desafio.android.dependency

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.api.PicPayService
import com.picpay.desafio.android.repository.PicPayRepository
import com.picpay.desafio.android.ui.viewModel.MainViewModel
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
}

val repositoryModule = module {
    single {
        PicPayRepository(get())
    }
}

val apiModule = module {
    fun provideUseApi(retrofit: Retrofit): PicPayService {
        return retrofit.create(PicPayService::class.java)
    }

    single { provideUseApi(get()) }
}

val retrofitModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().create()
    }

    fun provideHttpClient() = OkHttpClient.Builder()
            .build()


    fun provideRetrofit(factory: Gson, client: OkHttpClient): Retrofit {
        val url = "http://careers.picpay.com/tests/mobdev/"
        return Retrofit.Builder()
            .baseUrl(url)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(factory))
            .build()
    }

    single { provideGson() }
    single { provideHttpClient() }
    single { provideRetrofit(get(), get()) }
}
