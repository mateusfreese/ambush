package com.ambush.ambushchallenge.data

import com.ambush.ambushchallenge.data.model.AmbushRepository
import com.ambush.ambushchallenge.data.remote.ResultWrapper

interface Repository {
    suspend fun getAmbushRepositoryLanguageList(): ResultWrapper<List<AmbushRepository>>

    suspend fun getAmbushRepositoryByLanguage(languageName: String): ResultWrapper<List<AmbushRepository>>
}
