package com.ambush.ambushchallenge.data.remote

import com.ambush.ambushchallenge.data.model.AmbushRepository
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    @GET("orgs/{org}/repos")
    suspend fun getAmbushRepos(
        @Path("org") org: String = "GetAmbush",
        @Query("per_page") perPage: Int = 50
    ): List<AmbushRepository>
}
