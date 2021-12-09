package com.sylas.newsapp.ui.fragments.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sylas.newsapp.data.local.model.NewsResponse
import com.sylas.newsapp.repository.NewsRepository
import com.sylas.newsapp.util.checkForInternetConnection
import com.sylas.newsapp.util.state.StateResource
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel constructor(
    private val repository: NewsRepository,
    context: Application
) : AndroidViewModel(context) {

    private val _getAll = MutableLiveData<StateResource<NewsResponse>>()
    val getAll: LiveData<StateResource<NewsResponse>>get() = _getAll

    init {
        fetchAll()
    }

    private fun fetchAll() = viewModelScope.launch {
        safeFetchAll()
    }

    private suspend fun safeFetchAll() = viewModelScope.launch {
        _getAll.value = StateResource.Loading()
        try {
            if (checkForInternetConnection(getApplication())){
                val response = repository.getAllRemote()
                _getAll.value = handleResponse(response)
            } else {
                _getAll.value = StateResource.Error("Sem conexão com a internet")
            }
        } catch (e: Exception) {
            _getAll.value = StateResource.Error("Artigos não encontrados: ${e.message}")
        }
        }

    private fun handleResponse(response: Response<NewsResponse>): StateResource<NewsResponse>? {
        if (response.isSuccessful) {
            response.body()?.let {values ->
                return StateResource.Success(values)
            }
        }
        return StateResource.Error(response.message())
    }
}