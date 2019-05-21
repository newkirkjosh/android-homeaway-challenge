package com.newkirkj.seattlesearch.networking.foursquare.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua Newkirk on 5/20/2019.
 */
data class VenueSearchResponseWrapper(@SerializedName("response") val response: VenueSearchResponse)

data class VenueSearchResponse(@SerializedName("venues") val venues: List<VenueSearchItem> = listOf())