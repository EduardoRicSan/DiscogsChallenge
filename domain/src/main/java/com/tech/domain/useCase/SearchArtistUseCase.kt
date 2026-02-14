package com.tech.domain.useCase

import com.tech.domain.repository.DiscogsRepository
import javax.inject.Inject

class SearchArtistUseCase @Inject constructor(
    private val repository: DiscogsRepository
) {
    suspend operator fun invoke(query: String, page: Int) =
        repository.searchArtist(query, page)
}
