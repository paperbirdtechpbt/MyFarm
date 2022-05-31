package com.pbt.myfarm.module

import com.pbt.myfarm.LoginViewModel
import com.pbt.myfarm.MainActivityViewModel
import com.pbt.myfarm.Service.ApiClient
import com.pbt.myfarm.Service.ApiInterFace
import com.pbt.myfarm.Util.AppUtils
import com.pbt.myfarm.repository.LoginRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


var viewModel = module {
    viewModel {
        LoginViewModel(get())
    }
    viewModel{
        MainActivityViewModel(get())
    }
}
var repositoryModule = module {
    factory { LoginRepository(get()) }
    factory { MainRepository(get()) }
}
var retrofitBuilderModule = module {
    single { provideMoshi() }
    single { provideApiServie(get()) }
}

fun provideApiServie(moshi: Moshi): ApiInterFace =
    Retrofit.Builder().run {
        baseUrl("https://farm.myfarmdata.io/")
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
    }.create(ApiInterFace::class.java)

fun provideMoshi(): Moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()