package com.sylas.newsapp.data.repository

import androidx.lifecycle.LiveData
import com.sylas.newsapp.data.local.db.ArticleDataBase
import com.sylas.newsapp.data.local.model.Article
import com.sylas.newsapp.data.remote.NewsAPI

class NewsRepository(

    private val api: NewsAPI,
    private val db: ArticleDataBase) {

    //Remote
    suspend fun getAllRemote() = api.getBreakingNews()
    suspend fun search(query: String) = api.searchNews(query)

    //Local
    fun getAll(): LiveData<List<Article>> = db.getArticleDao().getAll()
    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)
    suspend fun delete(article: Article) = db.getArticleDao().delete(article)


}