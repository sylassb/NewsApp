package com.sylas.newsapp.presenter.favorite

import com.sylas.newsapp.model.Article

interface FavoriteHome {

    fun showArticles(articles: List<Article>)
}