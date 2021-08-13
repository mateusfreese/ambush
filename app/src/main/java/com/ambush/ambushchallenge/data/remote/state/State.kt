package com.ambush.ambushchallenge.data.remote.state

import com.ambush.ambushchallenge.data.model.AmbushRepository
import com.ambush.ambushchallenge.data.model.Language

sealed class State {
    data class Loading(val show: Boolean) : State()
    data class Error(val errorMessage: String) : State()
    data class OnSuccessGetRepositoryLanguageList(val data: List<Language>) : State()
    data class OnSuccessGetRepositoryFilteredByLanguage(val data: List<AmbushRepository>) : State()
}
