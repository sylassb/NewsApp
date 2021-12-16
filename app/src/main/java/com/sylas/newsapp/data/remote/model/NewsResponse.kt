package com.sylas.newsapp.data.remote.model

import com.sylas.newsapp.data.local.model.Article

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)