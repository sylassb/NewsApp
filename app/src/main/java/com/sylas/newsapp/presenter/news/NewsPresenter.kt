package com.sylas.newsapp.presenter.news

import com.sylas.newsapp.data.local.model.NewsResponse
import com.sylas.newsapp.repository.NewsDataSource
import com.sylas.newsapp.presenter.ViewHome

class NewsPresenter(val view: ViewHome.View,
private val dataSource: NewsDataSource
) :NewsHome.Presenter {
    override fun requestAll() {
        this.view.showProgressBar()
        this.dataSource.getBreakingNews(this)
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