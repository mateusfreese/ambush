package com.ambush.ambushchallenge.data.local

import com.ambush.ambushchallenge.data.model.AmbushRepository
import com.ambush.ambushchallenge.data.remote.ResultWrapper
import com.ambush.ambushchallenge.extensions.call
import io.paperdb.Book

class LocalDataSourceImpl(private val local: Book) : LocalDataSource {

    override suspend fun addAmbushRepos(ambushRepositories: List<AmbushRepository>): ResultWrapper<Unit> {
        return call {
            local.write(AMBUSH_REPOS, ambushRepositories)
        }
    }

    override suspend fun getAmbushRepos(): ResultWrapper<List<AmbushRepository>> {
        return call {
            local.read(AMBUSH_REPOS)
        }
    }

    override suspend fun deleteAllAmbushRepos(): ResultWrapper<Unit> {
        return call {
            local.destroy()
        }
    }

    override suspend fun replaceAllAmbushRepos(ambushRepositories: List<AmbushRepository>): ResultWrapper<Unit> {
        return call {
            deleteAllAmbushRepos()
            addAmbushRepos(ambushRepositories)
        }
    }

    companion object {
        const val AMBUSH_REPOS = "AMBUSH_REPOS"
    }
}
