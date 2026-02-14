package com.tech.discogschallenge.presentation.viewmodel.search

import com.tech.domain.model.search.SearchArtistResult

data class SearchArtistState(
    val query: String = "",
    val artists: List<SearchArtistResult> = emptyList(),
    val page: Int = 1,
    val isLoading: Boolean = false,
    val endReached: Boolean = false,
    val error: String? = null
)

sealed class SearchArtistIntent {
    data class Search(val query: String) : SearchArtistIntent()
    object LoadNextPage : SearchArtistIntent()
}

sealed class SearchArtistSideEffect {
    data class ShowError(val message: String) : SearchArtistSideEffect()
}

