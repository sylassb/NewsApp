package com.sylas.newsapp.data.local.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sylas.newsapp.data.local.model.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  updateInsert(article: Article) : Long

    @Query("SELECT * FROM articles")
    fun getAll(): LiveData<List<Article>>

    @Delete
    suspend fun delete(article: Article)
}