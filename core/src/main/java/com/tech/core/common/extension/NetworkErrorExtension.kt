package com.tech.core.common.extension

import com.tech.core.network.NetworkErrorConstants
import com.tech.core.network.NetworkResult
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.SerializationException
import java.io.IOException

internal fun Throwable.toNetworkError(): NetworkResult.Error {
    return when (this) {

        is ClientRequestException -> mapClientError(this)

        is ServerResponseException -> mapServerError(this)

        is RedirectResponseException -> NetworkResult.Error(
            message = NetworkErrorConstants.Messages.UNEXPECTED_REDIRECT,
            code = response.status.value
        )

        is IOException -> NetworkResult.Error(
            message = NetworkErrorConstants.Messages.NO_INTERNET
        )

        is SerializationException -> NetworkResult.Error(
            message = NetworkErrorConstants.Messages.PARSING_ERROR
        )

        else -> NetworkResult.Error(
            message = message ?: NetworkErrorConstants.Messages.UNKNOWN_ERROR
        )
    }
}
private fun mapClientError(
    exception: ClientRequestException
): NetworkResult.Error {

    return when (exception.response.status) {

        HttpStatusCode.BadRequest -> NetworkResult.Error(
            message = NetworkErrorConstants.Messages.BAD_REQUEST,
            code = NetworkErrorConstants.Codes.BAD_REQUEST
        )

        HttpStatusCode.Unauthorized -> NetworkResult.Error(
            message = NetworkErrorConstants.Messages.UNAUTHORIZED,
            code = NetworkErrorConstants.Codes.UNAUTHORIZED
        )

        HttpStatusCode.Forbidden -> NetworkResult.Error(
            message = NetworkErrorConstants.Messages.FORBIDDEN,
            code = NetworkErrorConstants.Codes.FORBIDDEN
        )

        HttpStatusCode.NotFound -> NetworkResult.Error(
            message = NetworkErrorConstants.Messages.NOT_FOUND,
            code = NetworkErrorConstants.Codes.NOT_FOUND
        )

        HttpStatusCode.TooManyRequests -> NetworkResult.Error(
            message = NetworkErrorConstants.Messages.TOO_MANY_REQUESTS,
            code = NetworkErrorConstants.Codes.TOO_MANY_REQUESTS
        )

        else -> NetworkResult.Error(
            message = "Client error ${exception.response.status.value}",
            code = exception.response.status.value
        )
    }
}
private fun mapServerError(
    exception: ServerResponseException
): NetworkResult.Error {

    return when (exception.response.status) {

        HttpStatusCode.InternalServerError -> NetworkResult.Error(
            message = NetworkErrorConstants.Messages.INTERNAL_SERVER_ERROR,
            code = NetworkErrorConstants.Codes.INTERNAL_SERVER_ERROR
        )

        HttpStatusCode.BadGateway -> NetworkResult.Error(
            message = NetworkErrorConstants.Messages.BAD_GATEWAY,
            code = NetworkErrorConstants.Codes.BAD_GATEWAY
        )

        HttpStatusCode.ServiceUnavailable -> NetworkResult.Error(
            message = NetworkErrorConstants.Messages.SERVICE_UNAVAILABLE,
            code = NetworkErrorConstants.Codes.SERVICE_UNAVAILABLE
        )

        HttpStatusCode.GatewayTimeout -> NetworkResult.Error(
            message = NetworkErrorConstants.Messages.GATEWAY_TIMEOUT,
            code = NetworkErrorConstants.Codes.GATEWAY_TIMEOUT
        )

        else -> NetworkResult.Error(
            message = "Server error ${exception.response.status.value}",
            code = exception.response.status.value
        )
    }
}
