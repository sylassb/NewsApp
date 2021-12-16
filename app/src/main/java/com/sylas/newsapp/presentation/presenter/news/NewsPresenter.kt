package com.sylas.newsapp.presentation.presenter.news

import com.sylas.newsapp.data.remote.model.NewsResponse
import com.sylas.newsapp.data.repository.NewsDataSource
import com.sylas.newsapp.presentation.presenter.ViewHome

@Deprecated("REMOVE IT")
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