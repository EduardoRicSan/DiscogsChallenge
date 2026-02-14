package com.tech.domain.repository

import com.tech.core.network.NetworkResult
import com.tech.domain.model.getAlbumsByArtist.Album
import com.tech.domain.model.getArtistInfo.Artist
import com.tech.domain.model.search.SearchArtistResult
import kotlinx.coroutines.flow.Flow

interface DiscogsRepository {
    suspend fun searchArtist(
        query: String,
        page: Int
    ): Flow<NetworkResult<List<SearchArtistResult>>>

    suspend fun getArtistInfo(artistId: Int): Flow<NetworkResult<Artist>>
    suspend fun getAlbumsByArtist(artistId: Int, page: Int): Flow<NetworkResult<List<Album>>>
}
