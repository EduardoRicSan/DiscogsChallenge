package com.tech.domain.useCase.getAlbumsByArtist

import com.tech.domain.repository.DiscogsRepository
import javax.inject.Inject

/**
 * Domain use case that retrieves albums for a given artist.
 * Keeps the presentation layer decoupled from repository implementation.
 */
class GetAlbumsByArtistUseCase @Inject constructor(
    private val repository: DiscogsRepository
) {
    /** Fetches artist albums with pagination support. */
    suspend operator fun invoke(artistId: Int, page: Int) =
        repository.getAlbumsByArtist(artistId, page)
}

