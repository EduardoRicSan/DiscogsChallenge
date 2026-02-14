package com.tech.domain.useCase

import com.tech.domain.repository.DiscogsRepository
import javax.inject.Inject

class GetArtistInfoUseCase @Inject constructor(
    private val repository: DiscogsRepository
) {
    suspend operator fun invoke(artistId: Int) =
        repository.getArtistInfo(artistId)
}
