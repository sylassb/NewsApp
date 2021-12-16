package com.sylas.newsapp.presentation.presenter

import com.sylas.newsapp.data.local.model.Article

interface ViewHome {

    interface View {
        fun showProgressBar()
        fun showFailure(message: String)
        fun hideProgressBar()
        fun showArticles(articles: List<Article>)
    }

    interface Favorite {
        fun showArticles(articles: List<Article>)
    }
}