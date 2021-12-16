package com.sylas.newsapp.data.repository

import android.content.Context
import com.sylas.newsapp.data.local.db.ArticleDataBase
import com.sylas.newsapp.data.local.model.Article
import com.sylas.newsapp.data.remote.RetrofitInstance
import com.sylas.newsapp.presentation.presenter.favorite.FavoriteHome
import com.sylas.newsapp.presentation.presenter.news.NewsHome
import com.sylas.newsapp.presentation.presenter.search.SearchHome
import kotlinx.coroutines.*

class NewsDataSource(context: Context) {

    private var db: ArticleDataBase = ArticleDataBase(context)
    private var newsRepository: NewsRepository = NewsRepository(RetrofitInstance.api, db)

    fun getBreakingNews(callback: NewsHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.getBreakingNews("br")
            if (response.isSuccessful){
                response.body()?.let {newsResponse ->
                    callback.onSucess(newsResponse)
                }
                callback.onComplete()
            } else {
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun searchNews(term: String, callback: SearchHome.Presenter) {
        GlobalScope.launch(Dispatchers.Main) {
        val response = RetrofitInstance.api.searchNews(term)
        if (response.isSuccessful){
            response.body()?.let { newsResponse ->
                callback.onSucess(newsResponse)
            }
            callback.onComplete()
        } else {
            callback.onError(response.message())
            callback.onComplete()
            }
        }
    }

    fun saveArticle(article: Article) {
        GlobalScope.launch(Dispatchers.Main) {
            newsRepository.updateInsert(article)
        }
    }

    fun getAllArticle(callback: FavoriteHome.Presenter) {
        var allArticles: List<Article>
        CoroutineScope(Dispatchers.IO).launch {
            allArticles = newsRepository.getAll().value!!

            withContext(Dispatchers.Main) {
                callback.onSuccess(allArticles)
            }
        }
    }

    fun deleteArticle(article: Article?) {
        GlobalScope.launch(Dispatchers.Main) {
            article?.let { articleSafe ->
                newsRepository.delete(articleSafe)
            }
        }
    }
}

