package com.tech.core

import com.tech.core.TestFactory.BASE_URL_MOCK
import com.tech.core.common.extension.toNetworkError
import com.tech.core.network.NetworkErrorConstants
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.SerializationException
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class NetworkClientErrorsDynamicTest {

    private val cases = listOf(
        ErrorCase(
            HttpStatusCode.BadRequest,
            NetworkErrorConstants.Messages.BAD_REQUEST,
            NetworkErrorConstants.Codes.BAD_REQUEST
        ),
        ErrorCase(
            HttpStatusCode.Unauthorized,
            NetworkErrorConstants.Messages.UNAUTHORIZED,
            NetworkErrorConstants.Codes.UNAUTHORIZED
        ),
        ErrorCase(
            HttpStatusCode.Forbidden,
            NetworkErrorConstants.Messages.FORBIDDEN,
            NetworkErrorConstants.Codes.FORBIDDEN
        ),
        ErrorCase(
            HttpStatusCode.NotFound,
            NetworkErrorConstants.Messages.NOT_FOUND,
            NetworkErrorConstants.Codes.NOT_FOUND
        ),
        ErrorCase(
            HttpStatusCode.TooManyRequests,
            NetworkErrorConstants.Messages.TOO_MANY_REQUESTS,
            NetworkErrorConstants.Codes.TOO_MANY_REQUESTS
        )
    )

    @Test
    fun `all client errors are mapped correctly`() = runTest {

        cases.forEach { case ->

            val client = createClientWithStatus(case.status)

            val error = try {
                client.get(BASE_URL_MOCK)
                null
            } catch (t: Throwable) {
                t.toNetworkError()
            }

            assertEquals(case.expectedMessage, error?.message)
            assertEquals(case.expectedCode, error?.code)
        }
    }

    @Test
    fun `all server errors are mapped correctly`() = runTest {

        val serverCases = listOf(
            ErrorCase(
                HttpStatusCode.InternalServerError,
                NetworkErrorConstants.Messages.INTERNAL_SERVER_ERROR,
                500
            ),
            ErrorCase(
                HttpStatusCode.BadGateway,
                NetworkErrorConstants.Messages.BAD_GATEWAY,
                502
            ),
            ErrorCase(
                HttpStatusCode.ServiceUnavailable,
                NetworkErrorConstants.Messages.SERVICE_UNAVAILABLE,
                503
            ),
            ErrorCase(
                HttpStatusCode.GatewayTimeout,
                NetworkErrorConstants.Messages.GATEWAY_TIMEOUT,
                504
            )
        )

        serverCases.forEach { case ->

            val client = createClientWithStatus(case.status)

            val error = try {
                client.get("https://test.com")
                null
            } catch (t: Throwable) {
                t.toNetworkError()
            }

            assertEquals(case.expectedMessage, error?.message)
            assertEquals(case.expectedCode, error?.code)
        }
    }

    @Test
    fun `IOException maps correctly`() {
        val error = IOException().toNetworkError()

        assertEquals(
            NetworkErrorConstants.Messages.NO_INTERNET,
            error.message
        )
    }

    @Test
    fun `SerializationException maps correctly`() {
        val error = SerializationException().toNetworkError()

        assertEquals(
            NetworkErrorConstants.Messages.PARSING_ERROR,
            error.message
        )
    }

    @Test
    fun `unknown exception fallback`() {
        val error = Exception().toNetworkError()

        assertEquals(
            NetworkErrorConstants.Messages.UNKNOWN_ERROR,
            error.message
        )
    }


}

