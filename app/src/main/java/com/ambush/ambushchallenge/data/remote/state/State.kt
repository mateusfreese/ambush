package com.ambush.ambushchallenge.data.remote.state

import com.ambush.ambushchallenge.data.model.AmbushRepository
import com.ambush.ambushchallenge.data.model.Language

sealed class State {
    class Loading(val show: Boolean) : State()
    class Error(val errorMessage: String) : State()
    class OnSuccessGetRepositoryLanguageList(val data: List<Language>) : State()
    class OnSuccessGetRepositoryFilteredByLanguage(val data: List<AmbushRepository>) : State()
}
