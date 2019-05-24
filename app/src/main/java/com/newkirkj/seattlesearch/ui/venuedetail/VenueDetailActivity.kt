package com.newkirkj.seattlesearch.ui.venuedetail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.newkirkj.seattlesearch.R
import com.newkirkj.seattlesearch.networking.foursquare.models.VenueSearchItem
import com.newkirkj.seattlesearch.utils.pixelToDp
import com.newkirkj.seattlesearch.utils.screenWidth
import kotlinx.android.synthetic.main.activity_venue_detail.*
import kotlinx.android.synthetic.main.content_venue_detail.*

class VenueDetailActivity : AppCompatActivity(), AppBarLayout.OnOffsetChangedListener,
    VenueDetailViewModel.VenueDetailContract {

    private lateinit var viewModel: VenueDetailViewModel

    private var venueSearchItem: VenueSearchItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venue_detail)
        setSupportActionBar(toolbar)
        setupActionBar()
        parseSavedInstanceState(intent.extras)
        setupViewModel()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        parseSavedInstanceState(savedInstanceState)
        venueSearchItem?.let { viewModel.updateSearchItem(it) }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelable(VenueSearchItem.EXTRA_TAG, venueSearchItem)
        super.onSaveInstanceState(outState)
    }

    private fun setupActionBar() {
        toolbar_layout.setExpandedTitleColor(Color.TRANSPARENT)
        toolbar_layout.setCollapsedTitleTextColor(Color.WHITE)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        app_bar?.addOnOffsetChangedListener(this)
    }

    private fun parseSavedInstanceState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            if (it.containsKey(VenueSearchItem.EXTRA_TAG)) {
                venueSearchItem = savedInstanceState.getParcelable(VenueSearchItem.EXTRA_TAG)
            }
        }
    }

    @SuppressLint("PrivateResource")
    private fun setupViewModel() {
        setupViewListeners()
        if (venueSearchItem != null) {
            val imageHeight = resources.getDimensionPixelOffset(R.dimen.app_bar_height)
            val viewModelFactory =
                VenueDetailViewModelFactory(application, this, venueSearchItem!!, screenWidth(), imageHeight)
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(VenueDetailViewModel::class.java)
            viewModel.getStaticMapURL().observe(this, Observer<String> { mapURL ->
                Glide.with(this@VenueDetailActivity)
                    .load(mapURL)
                    .override(static_map_image.width.toFloat().pixelToDp(this@VenueDetailActivity), imageHeight)
                    .into(static_map_image)
            })
            viewModel.getVenueSearchItem().observe(this, Observer<VenueSearchItem> { searchItem ->
                // Basic Info
                venue_name?.text = searchItem.name
                val primaryCat = searchItem.categories.first { it.primary }
                venue_category_name?.text = primaryCat.name

                Glide.with(this@VenueDetailActivity)
                    .load(primaryCat.icon.imageURLString())
                    .centerCrop()
                    .into(venue_category_image)

                venue_category_image?.setColorFilter(
                    resources.getColor(
                        R.color.accent_material_light,
                        resources.newTheme()
                    ), PorterDuff.Mode.SRC_ATOP
                )

                // Contact Information
                venue_contact_phone_container?.visibility = if (searchItem.contact == null) View.GONE else View.VISIBLE
                venue_contact_phone_text?.text = searchItem.contact?.formattedPhone

                venue_contact_website_container?.visibility =
                    if (searchItem.url.isNullOrEmpty()) View.GONE else View.VISIBLE
                venue_contact_website_text?.text = searchItem.url

                // Location Information
                venue_location_container?.visibility = if (searchItem.location == null) View.GONE else View.VISIBLE
                venue_location_address_text?.text = searchItem.location?.formattedAddress()
            })
        } else {
            // Would need to handle error in some other fashion
            Log.wtf(TAG, "VenueSearchItem is null, this shouldn't happen.")
        }
    }

    private fun setupViewListeners() {
        venue_contact_website_container?.setOnClickListener { viewModel.websiteContainerClicked() }
    }

    // VenueDetailViewModel.VenueDetailContract

    override fun launchExternalWebsite(intent: Intent) {
        Log.d(TAG, "launchExternalWebsite: $intent")
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    // AppBarLayout.OnOffsetChangedListener

    override fun onOffsetChanged(appBar: AppBarLayout, offset: Int) {
        val percentage = (Math.abs(offset) / appBar.totalScrollRange.toFloat())
        overlay.alpha = percentage
        static_map_image.alpha = (1 - percentage)
    }

    companion object {
        private const val TAG = "VenueDetailActivity"
    }
}
