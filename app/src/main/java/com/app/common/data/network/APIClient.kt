package com.app.common.data.network

import com.app.common.utils.AppConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

object APIClient {

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.LOGIN_URL)
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }
}