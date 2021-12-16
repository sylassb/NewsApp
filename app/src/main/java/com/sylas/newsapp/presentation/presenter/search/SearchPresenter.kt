package com.sylas.newsapp.presentation.presenter.search

import com.sylas.newsapp.data.remote.model.NewsResponse
import com.sylas.newsapp.data.repository.NewsDataSource
import com.sylas.newsapp.presentation.presenter.ViewHome

@Deprecated("REMOVE IT")
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