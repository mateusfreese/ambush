package com.ambush.ambushchallenge.data.remote.repository

import com.ambush.ambushchallenge.data.remote.ResultWrapper
import com.ambush.ambushchallenge.data.remote.Service
import com.ambush.ambushchallenge.data.remote.response.AmbushReposResponse
import com.ambush.ambushchallenge.extensions.call

class RepositoryImpl(private val service: Service) : Repository {
    override suspend fun getAmbushRepos(): ResultWrapper<List<AmbushReposResponse>> {
        return call {
            service.getAmbushRepos()
        }
    }
}
