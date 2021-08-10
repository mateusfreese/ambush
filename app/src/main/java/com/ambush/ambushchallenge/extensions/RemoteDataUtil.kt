package com.ambush.ambushchallenge.extensions

import com.ambush.ambushchallenge.data.remote.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> call(apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(Dispatchers.IO) {
        try {
            val response = apiCall.invoke()

            ResultWrapper.Success(response)
        } catch (e: Exception) {
            ResultWrapper.Error(e.toString())
        }
    }
}
