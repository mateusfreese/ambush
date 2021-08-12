package com.ambush.ambushchallenge.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ambush.ambushchallenge.data.model.Language
import com.ambush.ambushchallenge.data.remote.ResultWrapper
import com.ambush.ambushchallenge.data.remote.response.AmbushReposResponse
import com.ambush.ambushchallenge.data.remote.state.State
import com.ambush.ambushchallenge.data.repository.Repository
import kotlinx.coroutines.launch

class RepositoryViewModel(private val repository: Repository) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> get() = _state

    private val _languageRepos = MutableLiveData<List<AmbushReposResponse>>()
    val languageRepos: LiveData<List<AmbushReposResponse>> get() = _languageRepos

    private val _ambushReposResponse = MutableLiveData<List<AmbushReposResponse>>()

    fun loadAmbushRepos(isConnected: Boolean?) {
        _ambushReposResponse.value.let {
            when {
                !it.isNullOrEmpty() -> {
                    handlerGetAmbushReposSuccess(it)
                }
                isConnected == true -> {
                    getAmbushRepos()
                }
                isConnected == false -> {
                    getLocalAmbushRepos()
                }
            }
        }
    }

    private fun getAmbushRepos() {
        viewModelScope.launch {
            showLoading()
            when (val response = repository.getAmbushRepos()) {
                is ResultWrapper.Error -> {
                    getAmbushReposErrorHandler(response.error)
                }
                is ResultWrapper.Success -> {
                    handlerGetAmbushReposSuccess(response.value)
                }
            }
            dismissLoading()
        }
    }

    private fun getLocalAmbushRepos() {
        viewModelScope.launch {
            showLoading()
//            TODO("IMPLEMENT getLocalAmbushRepos")
            dismissLoading()
        }
    }

    fun getLanguageRepos(language: Language) {
        viewModelScope.launch {
            showLoading()
            handlerGetAmbushLanguageRepos(language)
            dismissLoading()
        }
    }

    private fun showLoading() {
        _state.value = State.Loading(true)
    }

    private fun dismissLoading() {
        _state.value = State.Loading(false)
    }

    private fun getAmbushReposErrorHandler(errorMessage: String) {
        _state.value = State.Error(errorMessage)
    }

    private fun handlerGetAmbushReposSuccess(response: List<AmbushReposResponse>) {
        _ambushReposResponse.value = response

        groupReposByLanguage(response).let {
            _state.value = State.OnSuccessGetAmbushRepos(it)
        }
    }

    private fun handlerGetAmbushLanguageRepos(language: Language) {
        _ambushReposResponse.value?.let {
            sortReposByIssues(it.filter { item -> item.language == language.name })
                .let { sortedList -> _languageRepos.value = sortedList }
            _state.value = State.OnSuccessSortAmbushLanguageRepos
        }
    }

    private fun groupReposByLanguage(repos: List<AmbushReposResponse>): List<Language> {
        val reposByLanguage = repos.groupBy { it.language }
        return reposByLanguage.map { Language(it.key, it.value.size) }
    }

    private fun sortReposByIssues(repos: List<AmbushReposResponse>): List<AmbushReposResponse> {
        return repos.sortedBy { it.openIssuesCount }
    }
}
