package com.newkirkj.seattlesearch.ui.venuedetail

import android.app.Application
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newkirkj.seattlesearch.R
import com.newkirkj.seattlesearch.networking.foursquare.models.VenueSearchItem
import com.newkirkj.seattlesearch.networking.foursquare.models.VenueSearchLocation

/**
 * Created by Joshua Newkirk on 5/23/2019.
 */
class VenueDetailViewModel(
    app: Application,
    private val contract: VenueDetailContract,
    private val searchItem: VenueSearchItem,
    private val screenWidth: Int,
    private val imageHeight: Int
) : AndroidViewModel(app) {

    interface VenueDetailContract {
        fun launchExternalWebsite(intent: Intent)
    }

    private val staticMapURL: MutableLiveData<String> = MutableLiveData()

    // TODO: Break up into individual pieces to keep formatting in ViewModel
    private val venueSearchItem: MutableLiveData<VenueSearchItem> = MutableLiveData()

    init {
        venueSearchItem.value = searchItem
        searchItem.location?.let {
            staticMapURL.value = formattedGoogleMapsURLString(it)
        }
    }

    fun updateSearchItem(searchItem: VenueSearchItem) {
        venueSearchItem.value = searchItem
        staticMapURL.value = searchItem.location?.let { formattedGoogleMapsURLString(it) }
    }

    fun websiteContainerClicked() {
        val uri = Uri.parse(searchItem.url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        contract.launchExternalWebsite(intent)
    }

    fun getStaticMapURL(): LiveData<String> {
        return staticMapURL
    }

    fun getVenueSearchItem(): LiveData<VenueSearchItem> {
        return venueSearchItem
    }

    private fun formattedGoogleMapsURLString(location: VenueSearchLocation): String {
        val mapsApiKey = getApplication<Application>().applicationContext.getString(R.string.access_token_google)
        return StringBuilder("https://maps.googleapis.com/maps/api/staticmap").apply {
            append("?center=Seattle,+WA") // Map Center
            append("&zoom=13") // Zoom Level
            append("&size=${screenWidth}x$imageHeight") // Map size, max free tier 640px x 640px
            append("&maptype=roadmap") // roadmap, satellite, hybrid
            append("&markers=color:green%7Clabel:S%7C47.6062,-122.3321") // Seattle, WA marker
            append("&markers=color:red%7C${location.lat},${location.lng}")
            append("&key=$mapsApiKey")
        }.toString()
    }
}