package com.sylas.newsapp.presentation.util.state

sealed class ArticleListEvent {
    object Fetch : ArticleListEvent()
}