package com.tech.data.remote.api

import com.tech.data.remote.dto.getAlbumsByArtist.GetAlbumsByArtistResponseDTO
import com.tech.data.remote.dto.getArtistInfo.GetArtistInfoResponseDTO
import com.tech.data.remote.dto.search.DiscogsArtistResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

/**
 * Concrete implementation of [DiscogsApiService] using Ktor [HttpClient].
 *
 * Responsible for building HTTP requests, attaching query parameters,
 * and mapping responses into DTOs returned by the Discogs API.
 */
class DiscogsApiServiceImpl(
    private val httpClient: HttpClient
) : DiscogsApiService {

    /** Executes artist search request with pagination support. */
    override suspend fun searchArtist(
        query: String,
        page: Int
    ): DiscogsArtistResponseDTO {
        return httpClient.get(DiscogsApiConstants.DISCOGS_SEARCH_ENDPOINT) {
            parameter(
                DiscogsApiQueryParameters.DISCOGS_SEARCH_QUERY_PARAMETER,
                query
            )
            parameter(
                DiscogsApiQueryParameters.DISCOGS_SEARCH_TYPE_PARAMETER,
                DiscogsApiQueryParameters.DISCOGS_SEARCH_TYPE_VALUE
            )
            parameter(
                DiscogsApiQueryParameters.DISCOGS_SEARCH_PER_PAGE_PARAMETER,
                DiscogsApiQueryParameters.DISCOGS_SEARCH_PER_PAGE_VALUE
            )
            parameter(
                DiscogsApiQueryParameters.DISCOGS_SEARCH_PAGE_PARAMETER,
                page
            )
        }.body()
    }

    /** Retrieves detailed artist information by id. */
    override suspend fun getArtistInfo(
        artistId: Int
    ): GetArtistInfoResponseDTO =
        httpClient.get(
            "${DiscogsApiConstants.DISCOGS_GET_ARTIST_INFO_ENDPOINT}$artistId"
        ).body()

    /** Retrieves artist albums/releases with pagination support. */
    override suspend fun getAlbumsByArtist(
        artistId: Int,
        page: Int
    ): GetAlbumsByArtistResponseDTO {
        return httpClient.get(
            "${DiscogsApiConstants.DISCOGS_GET_ARTIST_INFO_ENDPOINT}$artistId" +
                    DiscogsApiConstants.DISCOGS_GET_ARTIST_ALBUMS_ENDPOINT
        ) {
            parameter(
                DiscogsApiQueryParameters.DISCOGS_SEARCH_PER_PAGE_PARAMETER,
                DiscogsApiQueryParameters.DISCOGS_SEARCH_PER_PAGE_VALUE
            )
            parameter(
                DiscogsApiQueryParameters.DISCOGS_SEARCH_PAGE_PARAMETER,
                page
            )
        }.body()
    }
}
