package com.newkirkj.seattlesearch.ui.main

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newkirkj.seattlesearch.networking.foursquare.FoursquareVenueRepository
import com.newkirkj.seattlesearch.networking.foursquare.models.VenueSearchItem

/**
 * Created by Joshua Newkirk on 5/20/2019.
 */
class MainViewModel(
    private val contract: MainContract
) : ViewModel(), SearchView.OnQueryTextListener {

    interface MainContract {
        fun launchDetail(venueSearchItem: VenueSearchItem)
    }

    private val venueSearchItems: MutableLiveData<List<VenueSearchItem>> = MutableLiveData()
    private val searchResultsVisibility: MutableLiveData<Boolean> = MutableLiveData()
    private val welcomeMessageText: MutableLiveData<String> = MutableLiveData()

    fun refreshData() {
        venueSearchItems.value?.let {
            if (it.isNotEmpty()) {
                updateMutableLiveData(it)
            }
        }
    }

    fun getVenueSearchItems(): LiveData<List<VenueSearchItem>> {
        return venueSearchItems
    }

    fun getSearchResultsVisibility(): LiveData<Boolean> {
        return searchResultsVisibility
    }

    fun getWelcomeMessage(): LiveData<String> {
        return welcomeMessageText
    }

    fun searchItemClicked(position: Int) {
        Log.d(TAG, "searchItemClicked: $position")
        venueSearchItems.value?.let {
            contract.launchDetail(it[position])
        }
    }

    private fun searchVenues(query: String) {
        FoursquareVenueRepository.getVenues(query, object : FoursquareVenueRepository.VenueListCompletion {
            override fun onComplete(venues: List<VenueSearchItem>, error: String?) {
                if (error != null) {
                    Log.e(TAG, "Issues loading venues!")
                }
                updateMutableLiveData(venues)
            }
        })
    }

    private fun updateMutableLiveData(venues: List<VenueSearchItem>) {
        venueSearchItems.value = venues.sortedBy { it.location?.distance }
        searchResultsVisibility.value = venues.isNotEmpty()
        welcomeMessageText.value = if (venues.isEmpty()) {
            "Not enough minerals to make that request."
        } else {
            "Welcome to Seattle! Let\\'s find you something to do!"
        }
    }

    // SearchView.OnQueryTextListener

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query.count() > 3) {
            searchVenues(query)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true  // Do nothing for now
    }

    companion object {
        private const val TAG: String = "MainViewModel"
    }
}