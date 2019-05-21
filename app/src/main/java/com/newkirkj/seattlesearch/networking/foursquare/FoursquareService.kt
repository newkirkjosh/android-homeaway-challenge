package com.newkirkj.seattlesearch.networking.foursquare

import retrofit2.http.GET

/**
 * Created by Joshua Newkirk on 5/18/2019.
 */
interface FoursquareService {

    @GET("/venues/search")
    fun getVenues()
}