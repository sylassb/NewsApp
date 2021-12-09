package com.sylas.newsapp.util.state

sealed class ArticleListEvent {
    object Fetch : ArticleListEvent()
}