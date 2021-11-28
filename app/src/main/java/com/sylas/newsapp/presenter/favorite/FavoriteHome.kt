package com.sylas.newsapp.presenter.favorite

import com.sylas.newsapp.model.Article

interface FavoriteHome {

    interface Presenter {
        fun onSuccess(articles: List<Article>)
    }
}