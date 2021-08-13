package com.ambush.ambushchallenge.data

import com.ambush.ambushchallenge.data.local.LocalDataSource
import com.ambush.ambushchallenge.data.model.AmbushRepository
import com.ambush.ambushchallenge.data.model.Language
import com.ambush.ambushchallenge.data.remote.ResultWrapper
import com.ambush.ambushchallenge.data.remote.repository.RemoteDataSource
import com.ambush.ambushchallenge.extensions.call
import com.ambush.ambushchallenge.extensions.filterReposByLanguage
import com.ambush.ambushchallenge.extensions.groupReposByLanguage

class RepositoryImpl(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : Repository {

    override suspend fun getAmbushRepositoryLanguageList(): ResultWrapper<List<Language>> {
        return when (val remoteResponse = getAmbushRepositoryList()) {
            is ResultWrapper.Error -> remoteResponse
            is ResultWrapper.Success -> {
                ResultWrapper.Success(
                    remoteResponse.value.groupReposByLanguage()
                )
            }
        }
    }

    override suspend fun getAmbushRepositoryList(): ResultWrapper<List<AmbushRepository>> {
        return when (val remoteResponse = remote.getAmbushRepos()) {
            is ResultWrapper.Success -> {
                local.replaceAllAmbushRepos(remoteResponse.value)
                remoteResponse
            }
            is ResultWrapper.Error -> local.getAmbushRepos()
        }
    }

    override suspend fun getAmbushRepositoryByLanguage(languageName: String): ResultWrapper<List<AmbushRepository>> {
        return when (val response = getAmbushRepositoryList()) {
            is ResultWrapper.Error -> response
            is ResultWrapper.Success -> call { response.value.filterReposByLanguage(languageName) }
        }
    }
}
