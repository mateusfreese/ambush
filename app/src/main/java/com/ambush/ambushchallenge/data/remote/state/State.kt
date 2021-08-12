package com.ambush.ambushchallenge.data.remote.state

import com.ambush.ambushchallenge.data.model.Language

sealed class State {
    class Loading(val show: Boolean) : State()
    class Error(val errorMessage: String) : State()
    class OnSuccessGetAmbushRepos(val repos: List<Language>) : State()
    object OnSuccessSortAmbushLanguageRepos : State()
}
