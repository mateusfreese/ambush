package com.ambush.ambushchallenge.data.remote

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val error: String) : ResultWrapper<Nothing>()
}
