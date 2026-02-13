package com.tech.core.network

internal object NetworkErrorConstants {

    object Messages {

        // Generic
        const val UNKNOWN_ERROR = "Unknown error"
        const val UNEXPECTED_REDIRECT = "Unexpected redirect"

        // Connectivity
        const val NO_INTERNET = "No internet connection"

        // Serialization
        const val PARSING_ERROR = "Error parsing server response"

        // Client errors
        const val BAD_REQUEST = "Bad request"
        const val UNAUTHORIZED = "Unauthorized"
        const val FORBIDDEN = "Forbidden"
        const val NOT_FOUND = "Resource not found"
        const val TOO_MANY_REQUESTS = "Too many requests"

        // Server errors
        const val INTERNAL_SERVER_ERROR = "Internal server error"
        const val BAD_GATEWAY = "Bad gateway"
        const val SERVICE_UNAVAILABLE = "Service unavailable"
        const val GATEWAY_TIMEOUT = "Gateway timeout"
    }

    object Codes {
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val TOO_MANY_REQUESTS = 429

        const val INTERNAL_SERVER_ERROR = 500
        const val BAD_GATEWAY = 502
        const val SERVICE_UNAVAILABLE = 503
        const val GATEWAY_TIMEOUT = 504
    }
}
