package com.sylas.newsapp.presenter.favorite

import com.sylas.newsapp.data.local.model.Article
import com.sylas.newsapp.repository.NewsDataSource
import com.sylas.newsapp.presenter.ViewHome

class FavoritePresenter(
    val view: ViewHome.Favorite,
    private val datasSource: NewsDataSource
): FavoriteHome.Presenter {

    fun getAll(){
        this.datasSource.getAllArticle(this)
    }

    fun saveArticle(article: Article) {
        this.datasSource.saveArticle(article)
    }

    fun deleteArticle(article: Article) {
        this.datasSource.deleteArticle(article)
    }

    override fun onSuccess(articles: List<Article>) {
        this.view.showArticles(articles)
    }
}