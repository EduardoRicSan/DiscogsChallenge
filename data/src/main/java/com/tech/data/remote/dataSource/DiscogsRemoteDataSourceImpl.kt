package com.tech.data.remote.dataSource

import com.tech.core.network.NetworkResult
import com.tech.core.network.safeApiCall
import com.tech.data.remote.api.DiscogsApiService
import com.tech.data.remote.dto.getAlbumsByArtist.GetAlbumsByArtistResponseDTO
import com.tech.data.remote.dto.getArtistInfo.GetArtistInfoResponseDTO
import com.tech.data.remote.dto.search.DiscogsArtistResponseDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class DiscogsRemoteDataSourceImpl(
    private val apiService: DiscogsApiService
) : DiscogsRemoteDataSource {

    override suspend fun searchArtist(
        query: String,
        page: Int
    ): Flow<NetworkResult<DiscogsArtistResponseDTO>> =
        safeApiCall { apiService.searchArtist(query, page) }
            .flowOn(Dispatchers.IO)

    override suspend fun getArtistInfo(
        artistId: Int
    ): Flow<NetworkResult<GetArtistInfoResponseDTO>> =
        safeApiCall {
            apiService.getArtistInfo(artistId)
        }.flowOn(Dispatchers.IO)

    override suspend fun getAlbumsByArtist(
        artistId: Int,
        page: Int
    ): Flow<NetworkResult<GetAlbumsByArtistResponseDTO>> =
        safeApiCall {
            apiService.getAlbumsByArtist(artistId, page)
        }.flowOn(Dispatchers.IO)
}
