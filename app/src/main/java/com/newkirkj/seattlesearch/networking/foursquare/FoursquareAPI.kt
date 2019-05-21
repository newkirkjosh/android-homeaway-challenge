package com.newkirkj.seattlesearch.networking.foursquare

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.newkirkj.seattlesearch.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Joshua Newkirk on 5/19/2019.
 */
object FoursquareAPI {

    private const val BASE_URL = "https://api.foursquare.com/v2/"

    @JvmStatic
    fun foursquareService(): FoursquareService {
        return retrofit.create(FoursquareService::class.java)
    }

    private val okHttpClient: OkHttpClient by lazy {
        return@lazy OkHttpClient().newBuilder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        return@lazy Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val loggingInterceptor: HttpLoggingInterceptor by lazy {
        return@lazy HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
        }
    }

    private val gson: Gson by lazy {
        return@lazy GsonBuilder()
            .setDateFormat("yyy-MM-dd'T'HH:mm:ssZ")
            .create()
    }
}