package com.core.network

import retrofit2.Retrofit
import javax.inject.Inject

class RetrofitManager @Inject constructor() {

    @Inject
    lateinit var retrofit: Retrofit

    fun <T> createApiService(service: Class<T>): T = retrofit.create(service)
}
