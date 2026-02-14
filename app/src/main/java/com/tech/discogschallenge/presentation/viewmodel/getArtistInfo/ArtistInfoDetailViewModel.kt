package com.tech.discogschallenge.presentation.viewmodel.getArtistInfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.core.network.NetworkResult
import com.tech.domain.model.mapper.toFullDomain
import com.tech.domain.useCase.GetArtistInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class ArtistInfoDetailViewModel @Inject constructor(
    private val getArtistInfoUseCase: GetArtistInfoUseCase
) : ContainerHost<ArtistDetailState, ArtistDetailSideEffect>, ViewModel() {

    override val container =
        container<ArtistDetailState, ArtistDetailSideEffect>(ArtistDetailState())

    fun onIntent(intent: ArtistDetailIntent) = intent {
        when (intent) {
            is ArtistDetailIntent.LoadArtist -> loadArtist(intent.artistId)
            ArtistDetailIntent.Retry -> loadCurrentArtist()
            ArtistDetailIntent.ViewAlbums -> {
                state.artist?.let { artist ->
                    postSideEffect(ArtistDetailSideEffect.NavigateToAlbums(artist.id))
                }
            }
        }
    }

    private fun loadArtist(artistId: Int) = intent {
        reduce { state.copy(isLoading = true, errorMessage = null) }

        getArtistInfoUseCase(artistId)
            .catch { e ->
                reduce { state.copy(isLoading = false) }
                postSideEffect(ArtistDetailSideEffect.ShowError(e.message ?: "Unknown error"))
            }
            .collect { result ->
                when (result) {
                    is NetworkResult.Success -> reduce {
                        state.copy(isLoading = false, artist = result.data.toFullDomain())
                    }
                    is NetworkResult.Error -> {
                        reduce { state.copy(isLoading = false) }
                        postSideEffect(
                            ArtistDetailSideEffect.ShowError(result.message ?: "Unknown error")
                        )
                    }
                    is NetworkResult.Loading -> reduce { state.copy(isLoading = true) }
                }
            }
    }

    // Nueva función para Retry
    private fun loadCurrentArtist() {
        val artistId: Int? = container.stateFlow.value.artist?.id
        if (artistId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                onIntent(ArtistDetailIntent.LoadArtist(artistId))
            }
        }
    }
}


