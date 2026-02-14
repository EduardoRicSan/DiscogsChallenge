package com.tech.data.remote.dataSource

import com.tech.core.network.NetworkResult
import com.tech.data.remote.dto.getAlbumsByArtist.GetAlbumsByArtistResponseDTO
import com.tech.data.remote.dto.getArtistInfo.GetArtistInfoResponseDTO
import com.tech.data.remote.dto.search.DiscogsArtistResponseDTO
import kotlinx.coroutines.flow.Flow

/**
 * Remote data source contract responsible for communicating
 * with the Discogs API.
 *
 * Defines all network operations and returns raw DTO responses
 * wrapped in NetworkResult to standardize loading, success,
 * and error handling across the data layer.
 */
interface DiscogsRemoteDataSource {

    /** Requests artist search results from the API with pagination. */
    suspend fun searchArtist(
        query: String,
        page: Int,
    ): Flow<NetworkResult<DiscogsArtistResponseDTO>>

    /** Requests detailed information for a specific artist. */
    suspend fun getArtistInfo(
        artistId: Int,
    ): Flow<NetworkResult<GetArtistInfoResponseDTO>>

    /** Requests albums/releases for a given artist with pagination. */
    suspend fun getAlbumsByArtist(
        artistId: Int,
        page: Int,
    ): Flow<NetworkResult<GetAlbumsByArtistResponseDTO>>
}
