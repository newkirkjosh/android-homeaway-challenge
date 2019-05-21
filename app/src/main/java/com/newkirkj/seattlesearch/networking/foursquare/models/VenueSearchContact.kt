package com.newkirkj.seattlesearch.networking.foursquare.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua Newkirk on 5/20/2019.
 */
data class VenueSearchContact(
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("formattedPhone") var formattedPhone: String? = null,
    @SerializedName("twitter") var twitter: String? = null,
    @SerializedName("instagram") var instagram: String? = null
)