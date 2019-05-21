package com.newkirkj.seattlesearch.networking.foursquare

import com.newkirkj.seattlesearch.networking.foursquare.models.FoursquareUserlessAuth
import com.newkirkj.seattlesearch.networking.foursquare.models.VenueSearchResponseWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

/**
 * Created by Joshua Newkirk on 5/18/2019.
 */
interface FoursquareVenueService {

    @GET("venues/search")
    fun getVenues(
        @Query("near") near: String = "Seattle,+WA",
        @Query("query") query: String,
        @QueryMap auth: Map<String, String> = FoursquareUserlessAuth.authMap()
    ): Call<VenueSearchResponseWrapper>
}