package com.sylas.newsapp.ui.fragments.favorite

import androidx.lifecycle.*
import com.sylas.newsapp.data.local.model.Article
import com.sylas.newsapp.repository.NewsRepository
import com.sylas.newsapp.util.state.ArticleListEvent
import com.sylas.newsapp.util.state.ArticleListState
import kotlinx.coroutines.launch

class FavoriteViewModel constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _favorite = MutableLiveData<ArticleListEvent>()
    val favorite: LiveData<ArticleListState> = _favorite.switchMap {
        when(it){
            ArticleListEvent.Fetch -> getAll()
        }
    }

    fun dispatch(event: ArticleListEvent){
        this._favorite.postValue(event)
    }

    private fun getAll(): LiveData<ArticleListState> {
        return liveData {
            try {
                emit(ArticleListState.Loading)
                val listLiveDate = repository.getAll()
                    .map { list ->
                        if (list.isEmpty()) {
                            ArticleListState.Empty
                        } else {
                            ArticleListState.Success(list)
                        }
                    }
                emitSource(listLiveDate)
            } catch (e: Exception) {
                emit(ArticleListState.ErrorMessage("Algo deu errado"))
            }
        }
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.updateInsert(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.delete(article)
    }
}