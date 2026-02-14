package com.tech.discogschallenge.presentation.viewmodel.getAlbumsByArtist

import com.tech.domain.model.getAlbumsByArtist.Album
data class AlbumsByArtistState(
    val artistId: Int = 0,
    val albums: List<Album> = emptyList(),
    val selectedYear: Int? = null,
    val selectedGenre: String? = null,
    val selectedLabel: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)
sealed interface AlbumsByArtistIntent {
    data class LoadAlbums(val artistId: Int) : AlbumsByArtistIntent
    data class FilterByYear(val year: Int?) : AlbumsByArtistIntent
    data class FilterByGenre(val genre: String?) : AlbumsByArtistIntent
    data class FilterByLabel(val label: String?) : AlbumsByArtistIntent
    data object Retry : AlbumsByArtistIntent
}
sealed interface AlbumsByArtistSideEffect {
    data class ShowError(
        val message: String
    ) : AlbumsByArtistSideEffect
}
