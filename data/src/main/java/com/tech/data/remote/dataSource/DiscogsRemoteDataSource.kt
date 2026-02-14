package com.tech.data.remote.dataSource

import com.tech.core.network.NetworkResult
import com.tech.data.remote.dto.getAlbumsByArtist.GetAlbumsByArtistResponseDTO
import com.tech.data.remote.dto.getArtistInfo.GetArtistInfoResponseDTO
import com.tech.data.remote.dto.search.DiscogsArtistResponseDTO
import kotlinx.coroutines.flow.Flow

interface DiscogsRemoteDataSource {
    suspend fun searchArtist(
        query: String,
        page: Int,
    ): Flow<NetworkResult<DiscogsArtistResponseDTO>>

    suspend fun getArtistInfo(
        artistId: Int,
    ): Flow<NetworkResult<GetArtistInfoResponseDTO>>

    suspend fun getAlbumsByArtist(
        artistId: Int,
        page: Int,
    ): Flow<NetworkResult<GetAlbumsByArtistResponseDTO>>
}
