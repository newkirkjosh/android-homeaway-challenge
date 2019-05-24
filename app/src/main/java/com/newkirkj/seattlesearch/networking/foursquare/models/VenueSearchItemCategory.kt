package com.newkirkj.seattlesearch.networking.foursquare.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Joshua Newkirk on 5/20/2019.
 *
 * Reference: https://developer.foursquare.com/docs/api/venues/categories
 */
@Parcelize
data class VenueSearchItemCategory(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("pluralName") val pluralName: String,
    @SerializedName("shortName") val shortName: String,
    @SerializedName("icon") val icon: VenueSearchItemCategoryIcon,
    @SerializedName("primary") val primary: Boolean = false
) : Parcelable

/*
 * Pieces needed to construct category icons at various sizes. Combine prefix with a size (32, 44, 64, and 88 are
 * available) and suffix, e.g. https://foursquare.com/img/categories/food/default_64.png. To get an image with a gray
 * background, use bg_ before the size, e.g. https://foursquare.com/img/categories_v2/food/icecream_bg_32.png.
 */
@Parcelize
data class VenueSearchItemCategoryIcon(
    @SerializedName("prefix") val prefix: String,
    @SerializedName("suffix") val suffix: String
) : Parcelable {

    fun imageURLString(): String {
        return "${prefix}88$suffix"
    }
}