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
import com.tech.discogschallenge.R
import com.tech.core.common.extension.killApp
import com.tech.core.route.SearchArtist
import com.tech.design_system.common.model.DiscogsDialogMessage
import com.tech.design_system.common.model.asString
import com.tech.design_system.components.dialog.DiscogsAlertDialog
import com.tech.design_system.components.scafold.DiscogsAppScaffold
import com.tech.design_system.components.topBar.DiscogsTopBar
import com.tech.discogschallenge.presentation.ui.search.SearchArtistScreen
import com.tech.discogschallenge.presentation.viewmodel.DiscogsMainViewModel
import com.tech.discogschallenge.presentation.viewmodel.MainIntent
import com.tech.discogschallenge.presentation.viewmodel.MainSideEffect

@Composable
fun DiscogsNavHost(
    modifier: Modifier = Modifier,
    viewModel: DiscogsMainViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val state by viewModel.container.stateFlow.collectAsStateWithLifecycle()

    val showExitDialog = remember { mutableStateOf(false) }

    val context = LocalContext.current
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

    DiscogsAppScaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DiscogsTopBar(
                titleText = state.topBarTitle.asString(),
                currentRoute = state.currentRoute,
                onBackClick = {
                    if (state.currentRoute !is SearchArtist) {
                        navController.popBackStack()
                    }
                },
                onBackLongPress = {
                    if (state.currentRoute is SearchArtist) {
                        viewModel.onIntent(MainIntent.BackLongPressed)
                    }
                }
            )
        },
    ) { paddingValues, showTopSnackbar, triggerLoader ->
        NavHost(
            navController = navController,
            startDestination = SearchArtist,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable<SearchArtist> {
                LaunchedEffect(Unit) {
                    viewModel.onIntent(
                        MainIntent.RouteChanged(SearchArtist)
                    )
                }
                SearchArtistScreen(
                    showTopSnackbar = showTopSnackbar
                )
            }

        }
    }
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