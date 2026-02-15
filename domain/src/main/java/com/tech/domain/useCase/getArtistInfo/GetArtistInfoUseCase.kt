package com.tech.domain.useCase.getArtistInfo

import com.tech.domain.repository.DiscogsRepository
import javax.inject.Inject

/**
 * Domain use case that retrieves detailed artist information.
 * Keeps the presentation layer decoupled from data sources.
 */
class GetArtistInfoUseCase @Inject constructor(
    private val repository: DiscogsRepository
) {
    /** Fetches artist information by id. */
    suspend operator fun invoke(artistId: Int) =
        repository.getArtistInfo(artistId)
}
