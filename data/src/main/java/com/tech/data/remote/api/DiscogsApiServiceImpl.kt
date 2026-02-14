package com.tech.data.remote.api

import com.tech.data.remote.dto.DiscogsArtistResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class DiscogsApiServiceImpl(
    private val httpClient: HttpClient
) : DiscogsApiService {

    override suspend fun searchArtist(
        query: String,
        page: Int
    ): DiscogsArtistResponseDTO {
        return httpClient.get(DiscogsApiConstants.DISCOGS_SEARCH_ENDPOINT) {
            parameter(DiscogsApiQueryParameters.DISCOGS_SEARCH_QUERY_PARAMETER, query)
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

}
