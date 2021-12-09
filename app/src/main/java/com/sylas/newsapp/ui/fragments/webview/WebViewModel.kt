package com.sylas.newsapp.ui.fragments.webview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sylas.newsapp.data.local.model.Article
import com.sylas.newsapp.repository.NewsRepository
import kotlinx.coroutines.launch

class WebViewViewModel constructor(
    private val repository: NewsRepository
) : ViewModel() {

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.updateInsert(article)
    }
}

