package com.ambush.ambushchallenge.data.local

import com.ambush.ambushchallenge.data.model.AmbushRepository
import com.ambush.ambushchallenge.data.remote.ResultWrapper

interface LocalDataSource {

    suspend fun addAmbushRepos(ambushRepositories: List<AmbushRepository>): ResultWrapper<Unit>

    suspend fun getAmbushRepos(): ResultWrapper<List<AmbushRepository>>

    suspend fun deleteAllAmbushRepos(): ResultWrapper<Unit>

    suspend fun replaceAllAmbushRepos(ambushRepositories: List<AmbushRepository>): ResultWrapper<Unit>
}
