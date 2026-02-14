package com.tech.data.remote.dataSource

import com.tech.core.network.NetworkResult
import com.tech.data.remote.dto.DiscogsArtistResponseDTO
import kotlinx.coroutines.flow.Flow

interface DiscogsRemoteDataSource {
    suspend fun searchArtist(
        query: String,
        page: Int,
    ): Flow<NetworkResult<DiscogsArtistResponseDTO>>
}
