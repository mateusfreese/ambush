package com.ambush.ambushchallenge.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ambush.ambushchallenge.data.Repository
import com.ambush.ambushchallenge.data.remote.ResultWrapper
import com.ambush.ambushchallenge.data.remote.state.State
import com.ambush.ambushchallenge.extensions.sortReposByIssues
import kotlinx.coroutines.launch

class RepositoryViewModel(private val repository: Repository) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    fun getAmbushReposLanguageList() {
        viewModelScope.launch {
            showLoading()
            when (val response = repository.getAmbushRepositoryLanguageList()) {
                is ResultWrapper.Error -> _state.postValue(State.Error(response.error))
                is ResultWrapper.Success -> {
                    _state.value = State.OnSuccessGetRepositoryLanguageList(response.value)
                }
            }
            dismissLoading()
        }
    }

    fun getAmbushRepositoriesByLanguage(languageName: String) {
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
