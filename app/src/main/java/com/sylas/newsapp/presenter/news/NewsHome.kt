package com.sylas.newsapp.presenter.news

import com.sylas.newsapp.model.NewsResponse

interface NewsHome {

    interface Presenter{
        fun requestAll()
        fun onSucess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}