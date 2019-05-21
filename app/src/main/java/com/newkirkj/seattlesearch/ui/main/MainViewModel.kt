package com.newkirkj.seattlesearch.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newkirkj.seattlesearch.networking.foursquare.FoursquareVenueRepository
import com.newkirkj.seattlesearch.networking.foursquare.models.VenueSearchItem

/**
 * Created by Joshua Newkirk on 5/20/2019.
 */
class MainViewModel : ViewModel() {

    private val venueRepository = FoursquareVenueRepository

    private val venueSearchItems: MutableLiveData<List<VenueSearchItem>> = MutableLiveData()
    private val searchResultsVisibility: MutableLiveData<Boolean> = MutableLiveData()
    private val welcomeMessageText: MutableLiveData<String> = MutableLiveData()

    fun getVenueSearchItems(): LiveData<List<VenueSearchItem>> {
        return venueSearchItems
    }

    fun getSearchResultsVisibility(): LiveData<Boolean> {
        return searchResultsVisibility
    }

    fun getWelcomeMessage(): LiveData<String> {
        return welcomeMessageText
    }

    fun searchVenues(query: String) {
        FoursquareVenueRepository.getVenues(query, object : FoursquareVenueRepository.VenueListCompletion {
            override fun onComplete(venues: List<VenueSearchItem>, error: String?) {
                if (error != null) {
                    Log.e(TAG, "Issues loading venues!")
                }
                venueSearchItems.value = venues
                searchResultsVisibility.value = venues.isNotEmpty()
                welcomeMessageText.value = if (venues.isEmpty()) {
                    "Not enough minerals to make that request."
                } else {
                    "Welcome to Seattle! Let\\'s find you something to do!"
                }
            }
        })
    }

    companion object {
        private const val TAG: String = "MainViewModel"
    }
}