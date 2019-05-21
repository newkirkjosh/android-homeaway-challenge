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
    private const val CLIENT_ID = "CLIENT_ID"
    private const val CLIENT_SECRET = "CLIENT_SECRET"
    private const val CONTENT_TYPE = "Content-Type"

    // TODO: Pull from local file
    private val clientId: String = ""
    private val clientSecret: String = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()

        requestBuilder.header(CLIENT_ID, clientId)
            .header(CLIENT_SECRET, clientSecret)
            .header(ACCEPT, APPLICATION_JSON)

        return chain.proceed(requestBuilder.build())
    }
}