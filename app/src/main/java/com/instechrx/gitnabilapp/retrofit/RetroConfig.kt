package com.instechrx.gitnabilapp.retrofit

import com.instechrx.gitnabilapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroConfig {

    companion object Factory{
        fun getRetrofit() : ApiService {

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)

        }
    }
}