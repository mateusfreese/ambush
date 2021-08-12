package com.ambush.ambushchallenge.data.repository

import com.ambush.ambushchallenge.data.remote.ResultWrapper
import com.ambush.ambushchallenge.data.remote.response.AmbushReposResponse

interface Repository {
    suspend fun getAmbushRepos(): ResultWrapper<List<AmbushReposResponse>>
}
