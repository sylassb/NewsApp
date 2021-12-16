package com.sylas.newsapp.presentation.presenter.news

import com.sylas.newsapp.data.remote.model.NewsResponse

interface NewsHome {

    interface Presenter{
        fun requestAll()
        fun onSucess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}