package com.tech.core

import io.ktor.http.HttpStatusCode

data class ErrorCase(
    val status: HttpStatusCode,
    val expectedMessage: String,
    val expectedCode: Int
)
