package com.tech.domain.useCase.searchArtist

import com.tech.domain.repository.DiscogsRepository
import javax.inject.Inject

/**
 * Domain use case that handles artist search operations.
 * Keeps presentation layer decoupled from repository implementation.
 */
class SearchArtistUseCase @Inject constructor(
    private val repository: DiscogsRepository
) {
    /** Executes artist search with pagination support. */
    suspend operator fun invoke(query: String, page: Int) =
        repository.searchArtist(query, page)
}
