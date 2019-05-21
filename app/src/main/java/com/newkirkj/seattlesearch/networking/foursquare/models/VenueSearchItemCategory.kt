package com.newkirkj.seattlesearch.networking.foursquare.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Joshua Newkirk on 5/20/2019.
 */
data class VenueSearchItemCategory(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("pluralName") val pluralName: String,
    @SerializedName("shortName") val shortName: String,
    @SerializedName("icon") val icon: VenuSearchItemCategoryIcon,
    @SerializedName("primary") val primary: Boolean = false
)

data class VenuSearchItemCategoryIcon(
    @SerializedName("prefix") val prefix: String,
    @SerializedName("suffix") val suffix: String
) {
    fun imageUrlString(): String {
        return prefix + suffix
    }
}