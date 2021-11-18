package com.sylas.newsapp.presenter.search

import com.sylas.newsapp.model.NewsResponse
import com.sylas.newsapp.model.data.NewsDataSource
import com.sylas.newsapp.presenter.ViewHome

class SearchPresenter(val view: ViewHome.View,
private val dataSource: NewsDataSource): SearchHome.Presenter {
    override fun search(term: String) {
        TODO("Not yet implemented")
    }

    override fun onSucess(newsResponse: NewsResponse) {
        TODO("Not yet implemented")
    }

    override fun onError(message: String) {
        TODO("Not yet implemented")
    }

    override fun onComplete() {
        TODO("Not yet implemented")
    }
}