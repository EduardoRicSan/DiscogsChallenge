package com.tech.core.network

import com.tech.core.common.extension.toNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

/**
 * Executes a suspend API call safely and emits Loading, Success, or Error states
 * wrapped in [NetworkResult] using a Flow.
 */
suspend fun <T> safeApiCall(
    call: suspend () -> T
): Flow<NetworkResult<T>> = flow {
    emit(NetworkResult.Loading)
    val result = withContext(Dispatchers.IO) {
        call()
    }
    emit(NetworkResult.Success(result))
}.catch { throwable ->
    emit(throwable.toNetworkError())
}.flowOn(Dispatchers.IO)
