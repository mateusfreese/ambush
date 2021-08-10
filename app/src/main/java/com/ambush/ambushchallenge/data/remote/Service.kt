package com.ambush.ambushchallenge.data.remote

import com.ambush.ambushchallenge.data.remote.response.AmbushReposResponse
import retrofit2.http.GET

interface Service {
    @GET("users/GetAmbush/repos")
    suspend fun getAmbushRepos(): List<AmbushReposResponse>
}
