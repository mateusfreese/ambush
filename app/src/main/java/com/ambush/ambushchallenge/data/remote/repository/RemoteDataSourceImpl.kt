package com.ambush.ambushchallenge.data.remote.repository

import com.ambush.ambushchallenge.data.model.AmbushRepository
import com.ambush.ambushchallenge.data.remote.ResultWrapper
import com.ambush.ambushchallenge.data.remote.Service
import com.ambush.ambushchallenge.extensions.call

class RemoteDataSourceImpl(private val service: Service) : RemoteDataSource {
    override suspend fun getAmbushRepos(): ResultWrapper<List<AmbushRepository>> {
        return call {
            service.getAmbushRepos()
        }
    }
}
