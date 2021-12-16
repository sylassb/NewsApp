package com.sylas.newsapp.presentation.presenter.search

import com.sylas.newsapp.data.remote.model.NewsResponse

interface SearchHome {

    interface Presenter {
        fun search(term: String)
        fun onSucess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
    }
