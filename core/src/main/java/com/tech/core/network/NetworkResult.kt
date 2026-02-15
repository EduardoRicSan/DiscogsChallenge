package com.tech.core.network

/**
 * Represents the state of a network request.
 * Includes Success, Error, and Loading states to handle API responses consistently.
 */
sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String, val code: Int? = 0) : NetworkResult<Nothing>()
    data object Loading : NetworkResult<Nothing>()
}
