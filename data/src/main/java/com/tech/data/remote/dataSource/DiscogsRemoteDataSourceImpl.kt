package com.tech.data.remote.dataSource

import com.tech.core.network.NetworkResult
import com.tech.core.network.safeApiCall
import com.tech.data.remote.api.DiscogsApiService
import com.tech.data.remote.dto.DiscogsArtistResponseDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DiscogsRemoteDataSourceImpl(
    private val apiService: DiscogsApiService
): DiscogsRemoteDataSource {

    override suspend fun searchArtist(
        query: String,
        page: Int
    ): Flow<NetworkResult<DiscogsArtistResponseDTO>> =
        safeApiCall { apiService.searchArtist(query, page) }
            .flowOn(Dispatchers.IO)
}
