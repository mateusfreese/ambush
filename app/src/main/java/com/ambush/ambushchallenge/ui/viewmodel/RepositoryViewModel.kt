package com.ambush.ambushchallenge.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ambush.ambushchallenge.data.Repository
import com.ambush.ambushchallenge.data.remote.ResultWrapper
import com.ambush.ambushchallenge.data.remote.state.State
import com.ambush.ambushchallenge.extensions.groupReposByLanguage
import com.ambush.ambushchallenge.extensions.sortReposByIssues
import kotlinx.coroutines.launch

class RepositoryViewModel(private val repository: Repository) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    fun getAmbushReposByLanguage() {
        viewModelScope.launch {
            showLoading()
            when (val response = repository.getAmbushRepositoryLanguageList()) {
                is ResultWrapper.Error -> _state.postValue(State.Error(response.error))
                is ResultWrapper.Success -> {
                    response.value.groupReposByLanguage().let {
                        _state.postValue(State.OnSuccessGetRepositoryLanguageList(it))
                    }
                }
            }
            dismissLoading()
        }
    }

    fun getAmbushRepositories(languageName: String) {
        viewModelScope.launch {
            showLoading()
            when (val response = repository.getAmbushRepositoryByLanguage(languageName)) {
                is ResultWrapper.Error -> {
                    _state.postValue(State.Error(response.error))
                }
                is ResultWrapper.Success -> {
                    response.value
                        .sortReposByIssues()
                        .let { _state.postValue(State.OnSuccessGetRepositoryFilteredByLanguage(it)) }
                }
            }
            dismissLoading()
        }
    }

    private fun showLoading() {
        _state.value = State.Loading(true)
    }

    private fun dismissLoading() {
        _state.value = State.Loading(false)
    }
}
