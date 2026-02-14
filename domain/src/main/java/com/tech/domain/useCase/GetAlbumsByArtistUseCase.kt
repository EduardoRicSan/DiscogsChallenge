package com.tech.domain.useCase

import com.tech.domain.repository.DiscogsRepository
import javax.inject.Inject

class GetAlbumsByArtistUseCase @Inject constructor(
    private val repository: DiscogsRepository
) {
    suspend operator fun invoke(artistId: Int, page: Int) =
        repository.getAlbumsByArtist(artistId, page)
}
