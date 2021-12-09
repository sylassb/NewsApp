package com.sylas.newsapp.data.local.db

import androidx.room.TypeConverter
import com.sylas.newsapp.data.local.model.Source

class Converters {

    @TypeConverter
    fun fromSource(source: Source) : String{
        return source.name
    }

    @TypeConverter
    fun toSource(name: String) : Source {
        return Source(name, name)
    }
}