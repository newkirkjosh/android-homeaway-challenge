package com.newkirkj.seattlesearch.networking.foursquare.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua Newkirk on 5/20/2019.
 */
data class VenueSearchItem(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("contact") val contact: VenueSearchContact,
    @SerializedName("location") val location: VenueSearchLocation,
    @SerializedName("categories") val categories: List<VenueSearchItemCategory>,
    @SerializedName("url") val url: String? = null
)