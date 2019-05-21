package com.newkirkj.seattlesearch.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newkirkj.seattlesearch.R
import com.newkirkj.seattlesearch.networking.foursquare.models.VenueSearchItem
import com.newkirkj.seattlesearch.utils.bind

/**
 * Created by Joshua Newkirk on 5/20/2019.
 */
class VenueSearchRecyclerAdapter(
    private var venueSearchItems: List<VenueSearchItem>
) : RecyclerView.Adapter<VenueSearchRecyclerAdapter.VenueSearchItemViewHolder>() {

    fun updateSearchItems(venueSearchItems: List<VenueSearchItem>) {
        this.venueSearchItems = venueSearchItems.sortedBy {
            it.location.distance
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueSearchItemViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.venue_search_item, parent, false)
        return VenueSearchItemViewHolder(itemView)
    }

    override fun getItemCount(): Int = venueSearchItems.size

    override fun onBindViewHolder(holder: VenueSearchItemViewHolder, position: Int) {
        val venueSearchItem = venueSearchItems[position]
        holder.bind(venueSearchItem)
    }

    class VenueSearchItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val venueName by itemView.bind<TextView>(R.id.venue_name)
        private val venueCategoryName by itemView.bind<TextView>(R.id.venue_category_name)
        private val venueCategoryIcon by itemView.bind<ImageView>(R.id.venue_category_icon)
        private val venueDistanceTo by itemView.bind<TextView>(R.id.venue_distance_to)

        fun bind(venueSearchItem: VenueSearchItem) {
            venueName.text = venueSearchItem.name
            venueSearchItem.categories.firstOrNull { it.primary }?.let {
                venueCategoryName.text = it.name
                Glide.with(itemView)
                    .load(it.icon.imageUrlString())
                    .fitCenter()
                    .into(venueCategoryIcon)
            }
            venueDistanceTo.text = venueSearchItem.location.formattedDistanceInKilometers()
        }
    }
}