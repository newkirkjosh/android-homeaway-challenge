package com.newkirkj.seattlesearch.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.newkirkj.seattlesearch.ui.main.MainViewModel.MainContract

/**
 * Created by Joshua Newkirk on 5/20/2019.
 */
class MainViewModelFactory(private val contract: MainContract) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(contract) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}