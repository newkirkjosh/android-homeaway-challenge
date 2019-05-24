package com.newkirkj.seattlesearch.networking.foursquare.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Joshua Newkirk on 5/20/2019.
 */
@Parcelize
data class VenueSearchLocation(
    @SerializedName("address") val address: String? = null,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("distance") val distance: Int = -1, // Distance is measured in meters.
    @SerializedName("postalCode") val postalCode: String? = null,
    @SerializedName("city") val city: String? = null,
    @SerializedName("state") val state: String? = null
) : Parcelable {

    fun formattedAddress(): String = with(StringBuilder("")) {
        if (!address.isNullOrEmpty()) {
            append(address)
        }
        if (!city.isNullOrEmpty()) {
            if (this.isNotEmpty()) {
                append(", ")
            }
            append(city)
        }
        if (!state.isNullOrEmpty()) {
            if (this.isNotEmpty()) {
                append(", ")
            }
            append(state)
        }
        if (!postalCode.isNullOrEmpty()) {
            if (this.isNotEmpty()) {
                append(" $postalCode")
            }
        }
        toString()
    }

    private fun distanceInKilometers(): Float {
        return distance / 1000.0F
    }

    fun formattedDistanceInKilometers(): String {
        return String.format("%.2fkm away", distanceInKilometers())
    }

    private fun distanceInMiles(): Float {
        return distance / 1609.344F
    }

    fun formattedDistanceInMiles(): String {
        return String.format("%.2fmi away", distanceInMiles())
    }
}