package com.sylas.newsapp.presentation.util.state

import com.sylas.newsapp.data.local.model.Article

sealed class ArticleListState {
    data class Success(val list: List<Article>) : ArticleListState()
    data class ErrorMessage(val errorMessage: String) : ArticleListState()
    object Loading : ArticleListState()
    object Empty : ArticleListState()
}