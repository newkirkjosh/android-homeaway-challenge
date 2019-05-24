package com.newkirkj.seattlesearch.ui.venuedetail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.newkirkj.seattlesearch.networking.foursquare.models.VenueSearchItem

/**
 * Created by Joshua Newkirk on 5/23/2019.
 */
class VenueDetailViewModelFactory(
    private val app: Application,
    private val contract: VenueDetailViewModel.VenueDetailContract,
    private val venueSearchItem: VenueSearchItem,
    private val screenWidth: Int,
    private val imageHeight: Int
) : ViewModelProvider.AndroidViewModelFactory(app) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VenueDetailViewModel::class.java)) {
            return VenueDetailViewModel(app, contract, venueSearchItem, screenWidth, imageHeight) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}