package com.sijanneupane.mvvmnews.models

import com.sijanneupane.mvvmnews.models.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)