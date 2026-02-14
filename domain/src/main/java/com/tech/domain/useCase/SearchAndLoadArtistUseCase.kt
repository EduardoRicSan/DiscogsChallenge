package com.tech.domain.useCase

import com.tech.core.network.NetworkResult
import com.tech.domain.model.getArtistInfo.ArtistFull
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.toList

class SearchArtistFullUseCase @Inject constructor(
    private val searchArtistUseCase: SearchArtistUseCase,
    private val getArtistInfoUseCase: GetArtistInfoUseCase
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(query: String, page: Int): Flow<NetworkResult<List<ArtistFull>>> = flow {
        emit(NetworkResult.Loading)

        searchArtistUseCase(query, page)
            .catch { emit(NetworkResult.Error(it.message ?: "Search error")) }
            .collect { searchResult ->
                when (searchResult) {
                    is NetworkResult.Success -> {
                        val artists = searchResult.data.orEmpty()

                        // Limitar concurrencia para no disparar 429
                        val artistFullList = artists.asFlow()
                            .flatMapMerge(concurrency = 2) { artist ->
                                flow {
                                    try {
                                        val infoResult = getArtistInfoUseCase(artist.id)
                                            .first() // obtener el primer valor
                                        val full = if (infoResult is NetworkResult.Success) {
                                            ArtistFull(
                                                id = artist.id,
                                                name = infoResult.data.name,
                                                title = artist.title,
                                                thumb = artist.thumb,
                                                images = infoResult.data.images,
                                                members = infoResult.data.members,
                                                resourceUrl = artist.resourceUrl
                                            )
                                        } else {
                                            ArtistFull(
                                                id = artist.id,
                                                name = artist.title.orEmpty(),
                                                title = artist.title,
                                                thumb = artist.thumb,
                                                images = emptyList(),
                                                members = emptyList(),
                                                resourceUrl = artist.resourceUrl
                                            )
                                        }
                                        emit(full)
                                        delay(300) // pequeño delay para respetar rate limit
                                    } catch (e: Exception) {
                                        emit(
                                            ArtistFull(
                                                id = artist.id,
                                                name = artist.title.orEmpty(),
                                                title = artist.title,
                                                thumb = artist.thumb,
                                                images = emptyList(),
                                                members = emptyList(),
                                                resourceUrl = artist.resourceUrl
                                            )
                                        )
                                    }
                                }
                            }
                            .toList() // recolecta todos en una lista

                        emit(NetworkResult.Success(artistFullList))
                    }

                    is NetworkResult.Error -> emit(NetworkResult.Error(searchResult.message ?: "Search failed"))
                    is NetworkResult.Loading -> emit(NetworkResult.Loading)
                }
            }
    }.flowOn(Dispatchers.IO)
}


