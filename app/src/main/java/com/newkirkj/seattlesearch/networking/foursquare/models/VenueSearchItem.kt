package com.newkirkj.seattlesearch.networking.foursquare.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Joshua Newkirk on 5/20/2019.
 */
@Parcelize
data class VenueSearchItem(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("contact") val contact: VenueSearchContact? = null,
    @SerializedName("location") val location: VenueSearchLocation? = null,
    @SerializedName("categories") val categories: List<VenueSearchItemCategory> = listOf(),
    @SerializedName("url") val url: String? = "www.google.com"
) : Parcelable {

    companion object {
        const val EXTRA_TAG = "com.newkirkj.seattlesearch.networking.foursquare.models.VenueSearchItem"
    }
}