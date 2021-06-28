package com.sijanneupane.mvvmnews.db

import androidx.room.TypeConverter
import com.sijanneupane.mvvmnews.models.Source

class Converters {

    //Convert Source to String
    @TypeConverter
    fun fromSource(source: Source): String{
    return source.name
    }


    //Convert string to Source
    @TypeConverter
    fun toSource(name: String): Source{
    return Source(name, name)
    }
}