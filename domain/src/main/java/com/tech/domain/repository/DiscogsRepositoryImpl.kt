package com.tech.domain.repository

import com.tech.core.network.NetworkResult
import com.tech.data.remote.dataSource.DiscogsRemoteDataSource
import com.tech.domain.model.getAlbumsByArtist.Album
import com.tech.domain.model.getArtistInfo.Artist
import com.tech.domain.model.mapper.toDomain
import com.tech.domain.model.search.SearchArtistResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Repository implementation that coordinates data retrieval from the remote data source
 * and maps DTO models into domain models.
 *
 * Responsible for transforming API responses into domain-safe entities while exposing
 * reactive streams wrapped in NetworkResult states.
 */
class DiscogsRepositoryImpl @Inject constructor(
    private val remoteDataSource: DiscogsRemoteDataSource,
) : DiscogsRepository {

    /** Searches artists and maps API results into domain models. */
    override suspend fun searchArtist(
        query: String,
        page: Int
    ): Flow<NetworkResult<List<SearchArtistResult>>> =
        remoteDataSource.searchArtist(query, page)
            .map { result ->
                when (result) {
                    is NetworkResult.Success ->
                        NetworkResult.Success(
                            result.data.results.map { it.toDomain() }
                        )

                    is NetworkResult.Error ->
                        NetworkResult.Error(
                            message = result.message,
                            code = result.code
                        )

                    is NetworkResult.Loading -> NetworkResult.Loading
                }
            }
            .flowOn(Dispatchers.IO)

    /** Retrieves artist details and maps them into a domain model. */
    override suspend fun getArtistInfo(
        artistId: Int
    ): Flow<NetworkResult<Artist>> =
        remoteDataSource.getArtistInfo(artistId)
            .map { result ->
                when (result) {
                    is NetworkResult.Success ->
                        NetworkResult.Success(result.data.toDomain())

                    is NetworkResult.Error ->
                        NetworkResult.Error(
                            message = result.message,
                            code = result.code
                        )

                    is NetworkResult.Loading -> NetworkResult.Loading
                }
            }
            .flowOn(Dispatchers.IO)

    /** Retrieves artist albums and maps release DTOs into domain models. */
    override suspend fun getAlbumsByArtist(
        artistId: Int,
        page: Int
    ): Flow<NetworkResult<List<Album>>> =
        remoteDataSource.getAlbumsByArtist(artistId, page)
            .map { result ->
                when (result) {
                    is NetworkResult.Success ->
                        NetworkResult.Success(
                            result.data.releases.map { it.toDomain() }
                        )

                    is NetworkResult.Error ->
                        NetworkResult.Error(
                            message = result.message,
                            code = result.code
                        )

                    is NetworkResult.Loading -> NetworkResult.Loading
                }
            }
            .flowOn(Dispatchers.IO)
}
