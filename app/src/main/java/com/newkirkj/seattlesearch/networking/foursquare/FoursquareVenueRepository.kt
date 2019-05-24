package com.newkirkj.seattlesearch.networking.foursquare

import android.util.Log
import com.newkirkj.seattlesearch.networking.foursquare.models.VenueSearchItem
import com.newkirkj.seattlesearch.networking.foursquare.models.VenueSearchResponseWrapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Joshua Newkirk on 5/21/2019.
 */
object FoursquareVenueRepository {

    private const val TAG: String = "FoursquareVenueRepo"
    private val venueService: FoursquareVenueService =
        FoursquareRetrofitFactory.createService(FoursquareVenueService::class.java)

    interface VenueListCompletion {
        fun onComplete(venues: List<VenueSearchItem> = listOf(), error: String? = null)
    }

    // If there were more requests, we would create a queue and use a HashMap to control cancellations of requests
    private var venueSearchRequest: Pair<String, Call<VenueSearchResponseWrapper>>? = null

    fun getVenues(query: String, completion: VenueListCompletion) {
        Log.d(TAG, "getVenues hit with query: $query")
        if (venueSearchRequest != null && venueSearchRequest?.first == query) {
            return
        }

        val request = venueService.getVenues(query = query)
        venueSearchRequest = Pair(query, request)
        request.enqueue(object : Callback<VenueSearchResponseWrapper?> {
            override fun onFailure(call: Call<VenueSearchResponseWrapper?>, t: Throwable) {
                Log.e(TAG, "Error: $t") // Do nothing for now
                completion.onComplete(error = t.message)
                venueSearchRequest = null
            }

            override fun onResponse(
                call: Call<VenueSearchResponseWrapper?>,
                response: Response<VenueSearchResponseWrapper?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    response.body()?.let {
                        completion.onComplete(it.response.venues)
                    }
                } else {
                    completion.onComplete()
                }
                venueSearchRequest = null
            }
        })
    }
}