package com.tech.discogschallenge.presentation.ui.getAlbumsByArtist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tech.design_system.common.model.DiscogsSnackbarMessage
import com.tech.discogschallenge.presentation.viewmodel.getAlbumsByArtist.AlbumsByArtistIntent
import com.tech.discogschallenge.presentation.viewmodel.getAlbumsByArtist.AlbumsByArtistSideEffect
import com.tech.discogschallenge.presentation.viewmodel.getAlbumsByArtist.AlbumsByArtistViewModel
import com.tech.discogschallenge.presentation.viewmodel.getArtistInfo.ArtistInfoDetailViewModel

@Composable
fun AlbumsByArtistScreen(
    artistId: Int,
    triggerLoader: (Boolean) -> Unit,
    showTopSnackbar: (DiscogsSnackbarMessage) -> Unit,
    viewModel: AlbumsByArtistViewModel = hiltViewModel()
) {

    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    // LOAD DATA
    LaunchedEffect(artistId) {
        viewModel.onIntent(
            AlbumsByArtistIntent.LoadAlbums(artistId)
        )
    }

    // GLOBAL LOADER
    LaunchedEffect(state.isLoading) {
        triggerLoader(state.isLoading)
    }

    // SIDE EFFECTS
    LaunchedEffect(Unit) {
        viewModel.container.sideEffectFlow.collect { effect ->
            when (effect) {

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

    // UI
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
                .sortedByDescending { it.year ?: 0 }
                .toList()
        }
    }

    val availableYears by remember(state.albums) {
        derivedStateOf {
            state.albums
                .mapNotNull { it.year }
                .distinct()
                .sortedDescending()
        }
    }

    val availableLabels by remember(state.albums) {
        derivedStateOf {
            state.albums
                .mapNotNull { it.label }
                .distinct()
        }
    }

    AlbumsByArtistContent(
        state = state,
        albums = filteredAlbums,
        availableYears = availableYears,
        availableLabels = availableLabels,
        onIntent = viewModel::onIntent
    )
}

