package com.sijanneupane.mvvmnews.db

import android.content.Context
import androidx.room.*
import com.sijanneupane.mvvmnews.models.Article

/*
Database class for Room always need to be abstract
@Database annotation to let room know the class
pass parameter:
    List of entities, here we only have one single table: article

 */
@Database(
    entities = [Article::class],
    version = 1
)

@TypeConverters(Converters::class)

abstract class ArticleDatabase : RoomDatabase(){

    //function to return ArticleDao
    abstract fun getArticleDao(): ArticleDao

    //companion object to create an database
    companion object{
        //Volatile so that other threads can see immediately when thread changes this instance
        @Volatile
        private var instance: ArticleDatabase? =null
        //Lock variable to sync the instance, to make sure there is only one instance of ArticleDatabase at once
        private val LOCK= Any()

        //operator function called whenever we create the instance of our database
        //if instance is null we sync it with LOCK:
        //LOCK: so that anything that happens inside this function cant be accessed by other threads at the same time

        //sync instance if only null
        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            // null check "to make sure that there is not another thread that sets the instance to something while we already set it
            instance ?: createDatabase(context).also{ instance = it }

        }

        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java,
                "article_db.db"
            ).build()

    }
}