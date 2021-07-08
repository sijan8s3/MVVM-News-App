package com.sijanneupane.mvvmnews.models

import com.sijanneupane.mvvmnews.models.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)