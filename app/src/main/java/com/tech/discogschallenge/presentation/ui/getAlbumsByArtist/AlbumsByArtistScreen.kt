package com.tech.discogschallenge.presentation.ui.getAlbumsByArtist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tech.core.common.globalConstants.DiscogsGlobalConstants.ZERO_VALUE
import com.tech.design_system.common.model.DiscogsSnackbarMessage
import com.tech.discogschallenge.presentation.viewmodel.getAlbumsByArtist.AlbumsByArtistIntent
import com.tech.discogschallenge.presentation.viewmodel.getAlbumsByArtist.AlbumsByArtistSideEffect
import com.tech.discogschallenge.presentation.viewmodel.getAlbumsByArtist.AlbumsByArtistViewModel

// Screen responsible for displaying an artist's albums,
// handling data loading, global UI events, and filter application.
@Composable
fun AlbumsByArtistScreen(
    artistId: Int,
    triggerLoader: (Boolean) -> Unit,
    showTopSnackbar: (DiscogsSnackbarMessage) -> Unit,
    viewModel: AlbumsByArtistViewModel = hiltViewModel()
) {

    // Collects UI state from ViewModel lifecycle-aware
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    // Triggers initial albums loading when artistId changes
    LaunchedEffect(artistId) {
        viewModel.onIntent(
            AlbumsByArtistIntent.LoadAlbums(artistId)
        )
    }

    // Controls global loading indicator from screen state
    LaunchedEffect(state.isLoading) {
        triggerLoader(state.isLoading)
    }

    // Observes one-time side effects (errors, messages, etc.)
    LaunchedEffect(Unit) {
        viewModel.container.sideEffectFlow.collect { effect ->
            when (effect) {

                // Displays error message using global snackbar
                is AlbumsByArtistSideEffect.ShowError -> {
                    showTopSnackbar(
                        DiscogsSnackbarMessage(
                            message = effect.message
                        )
                    )
                }
            }
        }
    }

    // Applies filters and sorting locally for UI rendering
    val filteredAlbums by remember(
        state.albums,
        state.selectedYear,
        state.selectedGenre,
        state.selectedLabel
    ) {
        derivedStateOf {
            state.albums
                .asSequence()
                .filter {
                    state.selectedYear == null || it.year == state.selectedYear
                }
                .filter {
                    state.selectedGenre == null || it.type == state.selectedGenre
                }
                .filter {
                    state.selectedLabel == null || it.label == state.selectedLabel
                }
                .sortedByDescending { it.year ?: ZERO_VALUE }
                .toList()
        }
    }

    // Extracts available years for filter chips
    val availableYears by remember(state.albums) {
        derivedStateOf {
            state.albums
                .mapNotNull { it.year }
                .distinct()
                .sortedDescending()
        }
    }

    // Extracts available labels for filter chips
    val availableLabels by remember(state.albums) {
        derivedStateOf {
            state.albums
                .mapNotNull { it.label }
                .distinct()
        }
    }

    // Main screen content
    AlbumsByArtistContent(
        state = state,
        albums = filteredAlbums,
        availableYears = availableYears,
        availableLabels = availableLabels,
        onIntent = viewModel::onIntent
    )
}


