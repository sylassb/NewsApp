package com.sylas.newsapp.presentation.presenter.favorite

import com.sylas.newsapp.data.local.model.Article
import com.sylas.newsapp.data.repository.NewsDataSource
import com.sylas.newsapp.presentation.presenter.ViewHome

@Deprecated("REMOVE IT")
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