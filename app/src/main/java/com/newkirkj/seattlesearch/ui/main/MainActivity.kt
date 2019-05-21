package com.newkirkj.seattlesearch.ui.main

import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_search -> {
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init() {
        setupRecyclerView()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getVenueSearchItems().observe(this, Observer<List<VenueSearchItem>> { searchItems ->
            searchRecyclerAdapter?.updateSearchItems(searchItems)
        })
        viewModel.getSearchResultsVisibility().observe(this, Observer {
            search_recyclerview.visibility = if (it) View.VISIBLE else View.GONE
            welcome_message.visibility = if (it) View.GONE else View.VISIBLE
        })
        viewModel.getWelcomeMessage().observe(this, Observer {
            welcome_message.text = it
        })
    }

    private fun setupRecyclerView() {
        if (searchRecyclerAdapter == null) {
            searchRecyclerAdapter = VenueSearchRecyclerAdapter(listOf())
        }
        search_recyclerview.adapter = searchRecyclerAdapter
        search_recyclerview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query.count() > 3) {
            viewModel.searchVenues(query)
            return true
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true  // Do nothing for now
    }
}
