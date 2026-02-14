package com.tech.data.remote.api

import com.tech.data.remote.dto.getAlbumsByArtist.GetAlbumsByArtistResponseDTO
import com.tech.data.remote.dto.getArtistInfo.GetArtistInfoResponseDTO
import com.tech.data.remote.dto.search.DiscogsArtistResponseDTO

/**
 * API contract defining all Discogs network endpoints.
 *
 * This interface abstracts the HTTP layer and exposes
 * suspend functions used by the data layer to retrieve
 * remote information from the Discogs API.
 */
interface DiscogsApiService {

    /** Searches artists by query with pagination support. */
    suspend fun searchArtist(
        query: String,
        page: Int,
    ): DiscogsArtistResponseDTO

    /** Retrieves detailed information for a specific artist. */
    suspend fun getArtistInfo(
        artistId: Int,
    ): GetArtistInfoResponseDTO

    /** Retrieves albums/releases for a given artist with pagination. */
    suspend fun getAlbumsByArtist(
        artistId: Int,
        page: Int,
    ): GetAlbumsByArtistResponseDTO
}

