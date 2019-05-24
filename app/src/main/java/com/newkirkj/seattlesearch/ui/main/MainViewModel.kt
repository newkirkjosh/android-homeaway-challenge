package com.newkirkj.seattlesearch.ui.main

import android.os.Handler
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

    // Without going through the process of implementing a real debounce rx solution, this is a quick mimic
    // to not make a request every time the user types a character into the SearchView
    private var queryString: String? = null

    fun refreshData() {
        Log.d(TAG, "refreshData hit! venueSearchItems: ${venueSearchItems.value ?: "null"}")
        venueSearchItems.value?.let {
            Log.d(TAG, "venueSearchItems: ${it.count()}")
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
        // Base case: If the query is empty we can clear the results and avoid the request
        if (query.isEmpty()) {
            updateMutableLiveData(listOf())
            return
        }

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
        searchVenues(query ?: "")
        return false
    }

    // Depending on UX, we could change the debounce rate
    override fun onQueryTextChange(newText: String?): Boolean {
        if (!newText.isNullOrEmpty()) {
            if (queryString != newText) {
                queryString = newText
            }
            Handler().postDelayed({
                if (queryString == newText) {
                    searchVenues(newText)
                }
            }, 400)
        } else {
            searchVenues("")
        }
        return true  // Do nothing for now
    }

    companion object {
        private const val TAG: String = "MainViewModel"
    }
}