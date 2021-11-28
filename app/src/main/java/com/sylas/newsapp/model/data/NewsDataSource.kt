package com.sylas.newsapp.model.data

import android.content.Context
import com.sylas.newsapp.model.Article
import com.sylas.newsapp.model.db.ArticleDataBase
import com.sylas.newsapp.network.RetrofitInstance
import com.sylas.newsapp.presenter.favorite.FavoriteHome
import com.sylas.newsapp.presenter.news.NewsHome
import com.sylas.newsapp.presenter.search.SearchHome
import kotlinx.coroutines.*

class NewsDataSource(context: Context) {

    private var db: ArticleDataBase = ArticleDataBase(context)
    private var newsRepository: NewsRepository = NewsRepository(db)

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
            allArticles = newsRepository.getAll()

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

