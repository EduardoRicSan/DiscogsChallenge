package com.tech.discogschallenge.presentation.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.core.network.NetworkResult
import com.tech.domain.useCase.SearchArtistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

@HiltViewModel
class SearchArtistViewModel @Inject constructor(
    private val searchArtistUseCase: SearchArtistUseCase
) : ContainerHost<SearchArtistState, SearchArtistSideEffect>, ViewModel() {

    override val container =
        container<SearchArtistState, SearchArtistSideEffect>(SearchArtistState())

    // Job para manejar el debounce
    private var searchJob: Job? = null

    fun onIntent(intent: SearchArtistIntent) = intent {
        when (intent) {
            is SearchArtistIntent.Search -> {
                // Cancelamos la búsqueda anterior
                searchJob?.cancel()

                // Actualizamos el query y reseteamos la lista
                reduce { state.copy(query = intent.query, page = 1, artists = emptyList(), endReached = false) }

                // Lanzamos la búsqueda con debounce
                searchJob = viewModelScope.launch {
                    delay(500) // debounce de 500ms

                    if (intent.query.isNotEmpty()) {
                        searchArtist(intent.query, 1)
                    } else {
                        // Si el query está vacío, limpiamos resultados y loader
                        reduce { state.copy(artists = emptyList(), isLoading = false) }
                    }
                }
            }

            SearchArtistIntent.LoadNextPage -> {
                if (!state.isLoading && !state.endReached) {
                    searchArtist(state.query, state.page + 1)
                }
            }
        }
    }

    private fun searchArtist(query: String, page: Int) = intent {
        reduce { state.copy(isLoading = true, error = null) }

        searchArtistUseCase(query, page)
            .catch { e ->
                reduce { state.copy(isLoading = false) }
                postSideEffect(SearchArtistSideEffect.ShowError(e.message ?: "Unknown error"))
            }
            .collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val newArtists = result.data ?: emptyList()
                        reduce {
                            state.copy(
                                artists = if (page == 1) newArtists else state.artists + newArtists,
                                page = page,
                                isLoading = false,
                                endReached = newArtists.isEmpty()
                            )
                        }
                    }

                    is NetworkResult.Error -> {
                        reduce { state.copy(isLoading = false) }
                        postSideEffect(SearchArtistSideEffect.ShowError(result.message ?: "Unknown error"))
                    }

                    is NetworkResult.Loading -> {
                        reduce { state.copy(isLoading = true) }
                    }
                }
            }
    }
}






