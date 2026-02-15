package com.tech.data

import com.tech.data.remote.api.DiscogsApiService
import com.tech.data.remote.api.DiscogsApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
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

    private val searchArtistResponse = """
        {
            "pagination": {"page":1,"pages":1,"per_page":30,"items":1},
            "results": [{"id":1,"type":"artist","title":"Test Artist"}]
        }
    """.trimIndent()

    private val artistInfoResponse = """
        {
            "name": "Test Artist",
            "id": 1,
            "resource_url": "https://api.discogs.com/artists/1",
            "uri": "/artist/1",
            "releases_url": "/artist/1/releases"
        }
    """.trimIndent()

    private val albumsResponse = """
        {
            "pagination": {"page":1,"pages":1,"per_page":30,"items":1},
            "releases": [{"id":101,"title":"Test Album","year":2020}]
        }
    """.trimIndent()

    @Before
    fun setup() {
        client = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    when {
                        request.url.encodedPath.contains("/database/search") ->
                            respond(
                                content = searchArtistResponse,
                                status = HttpStatusCode.OK,
                                headers = headersOf(
                                    "Content-Type"
                                            to
                                            listOf(
                                                ContentType.Application.Json.toString()
                                            )
                                )
                            )

                        request.url.encodedPath.contains("/artists/") &&
                                !request.url.encodedPath.endsWith("/releases") ->
                            respond(
                                content = artistInfoResponse,
                                status = HttpStatusCode.OK,
                                headers = headersOf(
                                    "Content-Type"
                                            to
                                            listOf(
                                                ContentType.Application.Json.toString()
                                            )
                                )
                            )

                        request.url.encodedPath.endsWith("/releases") ->
                            respond(
                                content = albumsResponse,
                                status = HttpStatusCode.OK,
                                headers = headersOf(
                                    "Content-Type"
                                            to
                                            listOf(
                                                ContentType.Application.Json.toString()
                                            )
                                )
                            )

                        else -> respond(
                            content = "Not Found",
                            status = HttpStatusCode.NotFound
                        )
                    }
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

    @Test
    fun `getArtistInfo returns correct DTO`() = runTest {
        val result = service.getArtistInfo(1)
        assertEquals("Test Artist", result.name)
        assertEquals(1, result.id)
        assertEquals("https://api.discogs.com/artists/1", result.resourceUrl)
    }

    @Test
    fun `getAlbumsByArtist returns correct DTO`() = runTest {
        val result = service.getAlbumsByArtist(1, 1)
        assertEquals(1, result.pagination?.page)
        assertEquals(1, result.releases.size)
        assertEquals("Test Album", result.releases.first().title)
        assertEquals(2020, result.releases.first().year)
    }
}

