package com.tech.data

import com.tech.data.remote.api.DiscogsApiService
import com.tech.data.remote.api.DiscogsApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DiscogsApiServiceImplTest {

    private lateinit var service: DiscogsApiService
    private lateinit var client: HttpClient

    private val testResponse = """
        {
            "pagination": {"page":1,"pages":1,"per_page":30,"items":1},
            "results": [{"id":1,"type":"artist","title":"Test Artist"}]
        }
    """.trimIndent()

    @Before
    fun setup() {
        // Mock HTTP Client usando Ktor MockEngine
        client = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    respond(
                        content = testResponse,
                        headers = headersOf("Content-Type" to listOf("application/json"))
                    )
                }
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }

        service = DiscogsApiServiceImpl(client)
    }

    @Test
    fun `searchArtist returns correct DTO`() = runTest {
        val result = service.searchArtist("Test", 1)

        assertEquals(1, result.pagination?.page)
        assertEquals(1, result.results.size)
        assertEquals("Test Artist", result.results.first().title)
    }

}
