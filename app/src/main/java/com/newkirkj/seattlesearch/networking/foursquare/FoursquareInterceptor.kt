package com.newkirkj.seattlesearch.networking.foursquare

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Joshua Newkirk on 5/18/2019.
 */
object FoursquareInterceptor : Interceptor {

    // Header constants
    private const val ACCEPT = "Accept"
    private const val APPLICATION_JSON = "application/json"
    private const val CONTENT_TYPE = "Content-Type"

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.header(ACCEPT, APPLICATION_JSON)
        return chain.proceed(requestBuilder.build())
    }
}