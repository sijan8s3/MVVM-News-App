package com.sijanneupane.mvvmnews.ui

import androidx.lifecycle.ViewModel
import com.sijanneupane.mvvmnews.repository.NewsRepository

class NewsViewModel(
    val newsRepository: NewsRepository //parameter
) : ViewModel(){
}