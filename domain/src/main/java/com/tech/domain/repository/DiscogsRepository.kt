package com.tech.domain.repository

import com.tech.core.network.NetworkResult
import com.tech.domain.model.getAlbumsByArtist.Album
import com.tech.domain.model.getArtistInfo.Artist
import com.tech.domain.model.search.SearchArtistResult
import kotlinx.coroutines.flow.Flow

/**
 * Repository contract that defines all Discogs data operations.
 *
 * Acts as the single source of truth for the domain layer,
 * abstracting remote and local data sources from the rest
 * of the application following Clean Architecture principles.
 */
interface DiscogsRepository {
    /** Searches artists based on a query with pagination support. */
    suspend fun searchArtist(
        query: String,
        page: Int
    ): Flow<NetworkResult<List<SearchArtistResult>>>

    /** Retrieves detailed information for a specific artist. */
    suspend fun getArtistInfo(
        artistId: Int
    ): Flow<NetworkResult<Artist>>

    /** Retrieves albums for a given artist with pagination support. */
    suspend fun getAlbumsByArtist(
        artistId: Int,
        page: Int
    ): Flow<NetworkResult<List<Album>>>
}
