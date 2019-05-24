package com.newkirkj.seattlesearch.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newkirkj.seattlesearch.R
import com.newkirkj.seattlesearch.networking.foursquare.models.VenueSearchItem
import com.newkirkj.seattlesearch.ui.venuedetail.VenueDetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(),
    MainViewModel.MainContract,
    VenueSearchRecyclerAdapter.VenueSearchItemClickListener {

    private lateinit var viewModel: MainViewModel
    private var searchRecyclerAdapter: VenueSearchRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        init()

        fab.setOnClickListener { view ->
            Toast.makeText(
                view.context,
                "If time allows, implement full screen map with pins for search results.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onResume() {
        Log.d(TAG, "onResume hit")
        super.onResume()
        viewModel.refreshData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(viewModel)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> return true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init() {
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        val viewModelFactory = MainViewModelFactory(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getVenueSearchItems().observe(this, Observer<List<VenueSearchItem>> { searchItems ->
            searchRecyclerAdapter?.updateSearchItems(searchItems)
        })
        viewModel.getSearchResultsVisibility().observe(this, Observer {

            search_recyclerview?.visibility = if (it) View.VISIBLE else View.GONE
            if (it) fab?.show() else fab?.hide()
            welcome_message?.visibility = if (it) View.GONE else View.VISIBLE
        })
        viewModel.getWelcomeMessage().observe(this, Observer {
            welcome_message.text = it
        })
    }

    private fun setupRecyclerView() {
        if (searchRecyclerAdapter == null) {
            searchRecyclerAdapter = VenueSearchRecyclerAdapter(listOf(), this)
        }
        search_recyclerview.adapter = searchRecyclerAdapter
        search_recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    // VenueSearchRecyclerAdapter.VenueSearchItemClickListener

    override fun onItemClick(view: View, position: Int) {
        viewModel.searchItemClicked(position)
    }

    // MainViewModel.MainContract

    override fun launchDetail(venueSearchItem: VenueSearchItem) {
        Log.d(TAG, "launchDetail: for item ${venueSearchItem.id}")
        val intent = Intent(this@MainActivity, VenueDetailActivity::class.java)
        intent.putExtra(VenueSearchItem.EXTRA_TAG, venueSearchItem)
        startActivity(intent)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}
