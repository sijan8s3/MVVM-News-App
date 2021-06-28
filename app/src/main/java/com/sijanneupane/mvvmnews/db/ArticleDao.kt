package com.sijanneupane.mvvmnews.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sijanneupane.mvvmnews.models.Article

/*
Data Access Object
This is an interface for accessing database
Here we define functions to access the local database
save articles, read articles, delete articles
 */
@Dao //annotate to let room know that this is the interface that defines the function

interface ArticleDao {

    /*
    function to insert or update an article
    conflictStrategy is to define what to do, if conflict occurs in the database, like already exists in database
    here we, REPLACE
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article): Long // function(parameter):return --> here we return ID


    /*
    query function to return all articles available in our database
    LiveData: class of Android architecture components that enables fragments to subscribe to changes of live data
    when data changes, liveData will notify all the fragments so they can update the views
    useful in rotation
     */
    @Query("SELECT* FROM articles")
    fun getAllArticles():LiveData<List<Article>>


    /*
    Delete function
     */
    @Delete
    suspend fun deleteArticle(article: Article)
}