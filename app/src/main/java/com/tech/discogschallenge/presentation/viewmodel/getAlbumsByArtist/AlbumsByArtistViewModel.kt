package com.tech.discogschallenge.presentation.viewmodel.getAlbumsByArtist

import androidx.lifecycle.ViewModel
import com.tech.core.common.globalConstants.DiscogsGlobalConstants.ZERO_VALUE
import com.tech.core.network.NetworkResult
import com.tech.domain.useCase.getAlbumsByArtist.GetAlbumsByArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class AlbumsByArtistViewModel @Inject constructor(
    private val getAlbumsByArtistUseCase: GetAlbumsByArtistUseCase
) : ViewModel(),
    ContainerHost<AlbumsByArtistState, AlbumsByArtistSideEffect> {

    override val container =
        container<AlbumsByArtistState, AlbumsByArtistSideEffect>(
            AlbumsByArtistState()
        )

    fun onIntent(intent: AlbumsByArtistIntent) = intent {
        when (intent) {
            is AlbumsByArtistIntent.LoadAlbums -> {
                loadAlbums(intent.artistId)
            }

            is AlbumsByArtistIntent.FilterByYear -> {
                reduce { state.copy(selectedYear = intent.year) }
            }

            is AlbumsByArtistIntent.FilterByGenre -> {
                reduce { state.copy(selectedGenre = intent.genre) }
            }

            is AlbumsByArtistIntent.FilterByLabel -> {
                reduce { state.copy(selectedLabel = intent.label) }
            }

            AlbumsByArtistIntent.Retry -> {
                loadAlbums(state.artistId)
            }
        }
    }

    private fun loadAlbums(artistId: Int) = intent {
        reduce {
            state.copy(
                artistId = artistId,
                isLoading = true,
                error = null
            )
        }
        getAlbumsByArtistUseCase(artistId, page = 1)
            .collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        reduce { state.copy(isLoading = true) }
                    }

                    is NetworkResult.Error -> {
                        reduce {
                            state.copy(
                                isLoading = false,
                                error = result.message
                            )
                        }
                        postSideEffect(
                            AlbumsByArtistSideEffect.ShowError(
                                result.message
                            )
                        )
                    }

                    is NetworkResult.Success -> {
                        val sorted =
                            result.data
                                ?.sortedByDescending { it.year ?: ZERO_VALUE }
                                .orEmpty()
                        reduce {
                            state.copy(
                                albums = sorted,
                                isLoading = false
                            )
                        }
                    }
                }
            }
    }
}
