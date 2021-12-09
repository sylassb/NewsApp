package com.sylas.newsapp.presenter.search

import com.sylas.newsapp.data.local.model.NewsResponse

interface SearchHome {

    interface Presenter {
        fun search(term: String)
        fun onSucess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
    }
