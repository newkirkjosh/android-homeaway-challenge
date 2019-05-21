package com.newkirkj.seattlesearch.networking

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.newkirkj.seattlesearch.BuildConfig
import com.newkirkj.seattlesearch.networking.foursquare.FoursquareService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Joshua Newkirk on 5/18/2019.
 */
class FoursquareRepository(private val service: FoursquareService) {

    private val okHttpClient: OkHttpClient by lazy {
        return@lazy OkHttpClient().newBuilder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        return@lazy HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) BASIC else NONE
        }
    }

    private val gson: Gson by lazy {
        return@lazy GsonBuilder()
            .setDateFormat("yyy-MM-dd'T'HH:mm:ssZ")
            .create()
    }

    private val retrofit: Retrofit by lazy {
        return@lazy Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    companion object {
        private const val BASE_URL = "https://api.foursquare.com/v2/"
    }
}