package com.ambush.ambushchallenge.data.remote.response

import com.squareup.moshi.Json

data class AmbushReposResponse(
    @field:Json(name = "id")
    val id: Int?,

    @field:Json(name = "name")
    val name: String? = "",

    @field:Json(name = "created_at")
    val createdAt: String?,

    @field:Json(name = "updated_at")
    val updatedAt: String?,

    @field:Json(name = "language")
    val language: String?,

    @field:Json(name = "has_issues")
    val hasIssues: Boolean?,

    @field:Json(name = "open_issues")
    val openIssues: Int?,

    @field:Json(name = "open_issues_count")
    val openIssuesCount: Int?
)
