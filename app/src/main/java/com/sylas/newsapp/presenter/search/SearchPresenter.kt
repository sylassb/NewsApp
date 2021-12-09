package com.sylas.newsapp.presenter.search

import com.sylas.newsapp.data.local.model.NewsResponse
import com.sylas.newsapp.repository.NewsDataSource
import com.sylas.newsapp.presenter.ViewHome

class SearchPresenter(val view: ViewHome.View,
private val dataSource: NewsDataSource
): SearchHome.Presenter {

    override fun search(term: String) {
        this.view.showProgressBar()
        this.dataSource.searchNews(term,this)
    }

    override fun onSucess(newsResponse: NewsResponse) {
        this.view.showArticles(newsResponse.articles)
    }

    override fun onError(message: String) {
        this.view.showFailure(message)
    }

    override fun onComplete() {
        this.view.hideProgressBar()
    }
}