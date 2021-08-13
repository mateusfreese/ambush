package com.ambush.ambushchallenge.data.remote.repository

import com.ambush.ambushchallenge.data.model.AmbushRepository
import com.ambush.ambushchallenge.data.remote.ResultWrapper

interface RemoteDataSource {
    suspend fun getAmbushRepos(): ResultWrapper<List<AmbushRepository>>
}
