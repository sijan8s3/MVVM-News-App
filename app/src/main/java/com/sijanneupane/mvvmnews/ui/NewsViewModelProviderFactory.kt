package com.sijanneupane.mvvmnews.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sijanneupane.mvvmnews.repository.NewsRepository
/*
To define how our view model should be created
 */
class NewsViewModelProviderFactory(
    val newsRepository: NewsRepository
    ) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}