package com.ambush.ambushchallenge.extensions

import com.ambush.ambushchallenge.data.model.AmbushRepository
import com.ambush.ambushchallenge.data.model.Language

fun List<AmbushRepository>?.groupReposByLanguage(): List<Language> {
    return this?.groupBy { it.language }?.map { Language(it.key, it.value.size) } ?: listOf()
}

fun List<AmbushRepository>?.sortReposByIssues(): List<AmbushRepository> {
    return this?.sortedBy { it.openIssuesCount } ?: listOf()
}

fun List<AmbushRepository>?.filterReposByLanguage(languageName: String): List<AmbushRepository> {
    return this?.filter { item -> item.language == languageName } ?: listOf()
}
