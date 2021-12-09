package com.sylas.newsapp.presenter.favorite

import com.sylas.newsapp.data.local.model.Article

interface FavoriteHome {

    interface Presenter {
        fun onSuccess(articles: List<Article>)
    }
}