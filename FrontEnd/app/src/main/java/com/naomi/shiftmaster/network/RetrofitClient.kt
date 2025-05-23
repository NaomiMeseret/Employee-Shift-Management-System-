package com.naomi.shiftmaster.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.naomi.shiftmaster.network.ApiService
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:3000/api/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}