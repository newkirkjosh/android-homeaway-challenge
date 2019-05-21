package com.newkirkj.seattlesearch.networking.foursquare.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua Newkirk on 5/20/2019.
 */
data class VenueSearchLocation(
    @SerializedName("address") val address: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("distance") val distance: Int, // Distance is measured in meters.
    @SerializedName("postalCode") val postalCode: String,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String
) {

    fun formattedAddress(): String {
        return "$address, $city, $state $postalCode"
    }

    private fun distanceInKilometers(): Float {
        return distance / 1000.0F
    }

    fun formattedDistanceInKilometers(): String {
        return String.format("%.2fkm", distanceInKilometers())
    }

    private fun distanceInMiles(): Float {
        return distance / 1609.344F
    }

    fun formattedDistanceInMiles(): String {
        return String.format("%.2fmi", distanceInMiles())
    }
}