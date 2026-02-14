package com.tech.discogschallenge.presentation.navigation.navHost

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.tech.discogschallenge.R
import com.tech.core.common.extension.killApp
import com.tech.core.route.AlbumsByArtist
import com.tech.core.route.ArtistInfoDetail
import com.tech.core.route.SearchArtist
import com.tech.design_system.common.model.DiscogsDialogMessage
import com.tech.design_system.common.model.asString
import com.tech.design_system.components.dialog.DiscogsAlertDialog
import com.tech.design_system.components.scafold.DiscogsAppScaffold
import com.tech.design_system.components.topBar.DiscogsTopBar
import com.tech.discogschallenge.presentation.ui.getAlbumsByArtist.AlbumsByArtistScreen
import com.tech.discogschallenge.presentation.ui.getArtistInfo.ArtistInfoDetailScreen
import com.tech.discogschallenge.presentation.ui.search.SearchArtistScreen
import com.tech.discogschallenge.presentation.viewmodel.DiscogsMainViewModel
import com.tech.discogschallenge.presentation.viewmodel.MainIntent
import com.tech.discogschallenge.presentation.viewmodel.MainSideEffect

// Root navigation host that manages app navigation, global side effects,
// top bar behavior, and shared UI scaffolding across screens.
@Composable
fun DiscogsNavHost(
    modifier: Modifier = Modifier,
    viewModel: DiscogsMainViewModel = hiltViewModel()
) {

    // Navigation controller for Compose destinations
    val navController = rememberNavController()

    // Collects main UI state from ViewModel lifecycle-aware
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    // Controls exit confirmation dialog visibility
    val showExitDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current

    // Observes one-time side effects from ViewModel
    LaunchedEffect(Unit) {
        viewModel.container.sideEffectFlow.collect { effect ->
            when (effect) {
                MainSideEffect.ShowExitDialog -> {
                    showExitDialog.value = true
                }
                MainSideEffect.ExitApp -> {
                    context.killApp()
                }
                else -> Unit
            }
        }
    }

    // Main app scaffold with shared top bar
    DiscogsAppScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DiscogsTopBar(
                titleText = state.topBarTitle.asString(),
                currentRoute = state.currentRoute,

                // Handles back navigation between screens
                onBackClick = {
                    if (state.currentRoute !is SearchArtist) {
                        navController.popBackStack()
                    }
                },

                // Long press on root triggers exit flow
                onBackLongPress = {
                    if (state.currentRoute is SearchArtist) {
                        viewModel.onIntent(MainIntent.BackLongPressed)
                    }
                }
            )
        },
    ) { paddingValues, showTopSnackbar, triggerLoader ->

        // Navigation graph definition
        NavHost(
            navController = navController,
            startDestination = SearchArtist,
            modifier = Modifier.padding(paddingValues)
        ) {

            // SEARCH ARTIST SCREEN
            composable<SearchArtist> {
                LaunchedEffect(Unit) {
                    viewModel.onIntent(
                        MainIntent.RouteChanged(SearchArtist)
                    )
                }

                SearchArtistScreen(
                    showTopSnackbar = showTopSnackbar,
                    onArtistItemClick = { artistId ->
                        navController.navigate(ArtistInfoDetail(artistId))
                    }
                )
            }

            // ARTIST DETAIL SCREEN
            composable<ArtistInfoDetail> { backStackEntry ->
                val route = backStackEntry.toRoute<ArtistInfoDetail>()

                LaunchedEffect(Unit) {
                    viewModel.onIntent(
                        MainIntent.RouteChanged(
                            ArtistInfoDetail(route.artistId)
                        )
                    )
                }

                ArtistInfoDetailScreen(
                    triggerLoader = triggerLoader,
                    showTopSnackbar = showTopSnackbar,
                    artistId = route.artistId,
                    onNavigateToAlbums = { artistId ->
                        navController.navigate(AlbumsByArtist(artistId))
                    }
                )
            }

            // ALBUMS BY ARTIST SCREEN
            composable<AlbumsByArtist> { backStackEntry ->
                val route = backStackEntry.toRoute<AlbumsByArtist>()

                LaunchedEffect(Unit) {
                    viewModel.onIntent(
                        MainIntent.RouteChanged(
                            AlbumsByArtist(route.artistId)
                        )
                    )
                }

                AlbumsByArtistScreen(
                    triggerLoader = triggerLoader,
                    showTopSnackbar = showTopSnackbar,
                    artistId = route.artistId
                )
            }
        }
    }

    // Global exit confirmation dialog
    DiscogsAlertDialog(
        dialogMessage = DiscogsDialogMessage(
            title = stringResource(R.string.title_alert_dialog_exit),
            message = stringResource(R.string.message_alert_dialog_exit),
            confirmLabel = stringResource(R.string.confirm_alert_dialog_exit),
            dismissLabel = stringResource(R.string.dismiss_alert_dialog_exit),
            onConfirm = { viewModel.onIntent(MainIntent.ExitConfirmed) }
        ),
        isVisible = showExitDialog
    )
}
