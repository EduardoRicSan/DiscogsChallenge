package com.tech.core

import com.tech.core.TestFactory.APPLICATION_JSON_CONTENT_TYPE
import com.tech.core.TestFactory.ERROR_MESSAGE
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf

internal fun createClientWithStatus(status: HttpStatusCode): HttpClient {
    val engine = MockEngine { request ->
        respond(
            content = ERROR_MESSAGE,
            status = status,
            headers = headersOf(HttpHeaders.ContentType, APPLICATION_JSON_CONTENT_TYPE)
        )
    }

    return HttpClient(engine) {
        expectSuccess = true
    }
}

internal object TestFactory {
    const val ERROR_MESSAGE = "error"
    const val APPLICATION_JSON_CONTENT_TYPE = "application/json"
    const val BASE_URL_MOCK = "https://test.com"
}
