package com.sylas.newsapp.model.data

import com.sylas.newsapp.model.Article
import com.sylas.newsapp.model.db.ArticleDataBase

class NewsRepository(private val db: ArticleDataBase) {

    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)

    fun getAll(): List<Article> = db.getArticleDao().getAll()

    suspend fun delete(article: Article) = db.getArticleDao().delete(article)


}