package com.tech.discogschallenge.presentation.ui.getArtistInfo

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tech.design_system.common.model.DiscogsSnackbarMessage
import com.tech.design_system.common.model.DiscogsSnackbarType
import com.tech.discogschallenge.presentation.viewmodel.getArtistInfo.ArtistDetailIntent
import com.tech.discogschallenge.presentation.viewmodel.getArtistInfo.ArtistDetailSideEffect
import com.tech.discogschallenge.presentation.viewmodel.getArtistInfo.ArtistInfoDetailViewModel

@Composable
fun ArtistInfoDetailScreen(
    viewModel: ArtistInfoDetailViewModel = hiltViewModel(),
    triggerLoader: (Boolean) -> Unit,
    showTopSnackbar: (DiscogsSnackbarMessage) -> Unit,
    onNavigateToAlbums: (Int) -> Unit,
    artistId: Int,
) {
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current

    // Carga inicial del artista
    LaunchedEffect(artistId) {
        viewModel.onIntent(ArtistDetailIntent.LoadArtist(artistId))
    }

    // Loader global usando DiscogsAppScaffold
    triggerLoader(state.isLoading)

    Box(modifier = Modifier.fillMaxSize()) {

        // Contenido principal
        state.artist?.let { artist ->
            ArtistDetailContent(
                artist = artist,
                onViewAlbumsClick = { viewModel.onIntent(ArtistDetailIntent.ViewAlbums) }
            )
        }

        // Error centrado con snackbar del Design System
        state.errorMessage?.let { error ->
            showTopSnackbar(
                DiscogsSnackbarMessage(
                    message = error,
                    type = DiscogsSnackbarType.Error
                )
            )
        }
    }

    // SideEffects: navegación y errores
    LaunchedEffect(Unit) {
        viewModel.container.sideEffectFlow.collect { effect ->
            when (effect) {
                is ArtistDetailSideEffect.NavigateToAlbums -> {
                    onNavigateToAlbums(effect.artistId)
                }
                is ArtistDetailSideEffect.ShowError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}



