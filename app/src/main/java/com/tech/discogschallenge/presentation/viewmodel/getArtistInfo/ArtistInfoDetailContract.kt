package com.tech.discogschallenge.presentation.viewmodel.getArtistInfo

import com.tech.domain.model.getArtistInfo.ArtistFull
data class ArtistDetailState(
    val isLoading: Boolean = false,
    val artist: ArtistFull? = null,
    val errorMessage: String? = null,
)
sealed class ArtistDetailSideEffect {

    data class ShowError(val message: String) : ArtistDetailSideEffect()

    data class NavigateToAlbums(val artistId: Int) : ArtistDetailSideEffect()

    object NavigateToAppInfo : ArtistDetailSideEffect() // NEW
}

sealed class ArtistDetailIntent {
    data class LoadArtist(val artistId: Int) : ArtistDetailIntent()
    object Retry : ArtistDetailIntent()
    object ViewAlbums : ArtistDetailIntent()

    object ViewAppInfo : ArtistDetailIntent()
}
