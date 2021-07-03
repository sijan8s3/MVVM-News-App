package com.sijanneupane.mvvmnews.repository

import com.sijanneupane.mvvmnews.api.RetrofitInstance
import com.sijanneupane.mvvmnews.db.ArticleDatabase
/*
get data from database and from remote data source (retrofit api)
 */
class NewsRepository(
    val db: ArticleDatabase //parameter
) {

    /*
    function that directly queries our api for the breaking news
     */
    suspend fun getBreakingNews(countryCode:String, pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)
}