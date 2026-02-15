package com.tech.design_system.components.scafold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tech.design_system.common.model.DiscogsSnackbarMessage
import com.tech.design_system.components.box.DiscogsLoaderOverlay
import com.tech.design_system.components.loader.DiscogsLoadingIndicator
import com.tech.design_system.components.snackbar.BXMasTopSnackbar
import kotlinx.coroutines.delay

/**
 * [AppScaffold] is a generic wrapper around [Scaffold] that provides
 * a flexible structure for screens with a customizable top bar,
 * bottom navigation bar, and content area.
 *
 * This composable is designed to be reusable across the app
 * so that each screen can inject its own UI components.
 *
 * @param modifier Modifier to customize layout externally.
 * @param topBar Optional composable slot for the top app bar.
 * @param bottomBar Optional composable slot for the bottom navigation bar.
 * @param content Main content area of the screen.
 *
 * ### Recommended Usage
 * ```
 * AppScaffold(
 *     topBar = { MyTopBar(title = "Home") },
 *     bottomBar = { MyBottomNavigation() }
 * ) { paddingValues ->
 *     HomeScreenContent(Modifier.padding(paddingValues))
 * }
 * ```
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscogsAppScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (
        PaddingValues,
        (DiscogsSnackbarMessage) -> Unit,
        (Boolean) -> Unit,
    ) -> Unit
) {
    val topSnackbarMessages = remember { mutableStateListOf<DiscogsSnackbarMessage>() }
    var showLoader by remember { mutableStateOf(false) }

    val triggerLoader: (Boolean) -> Unit = { showLoader = it }

    Box(modifier = modifier.fillMaxSize()) {
        Scaffold(
            topBar = topBar,
            bottomBar = bottomBar
        ) { innerPadding ->
            content(innerPadding, { topSnackbarMessages.add(it) }, triggerLoader, )
        }

        // Snackbar
        if (topSnackbarMessages.isNotEmpty()) {
            val currentMessage = topSnackbarMessages.first()
            val isVisible = remember(currentMessage) { mutableStateOf(true) }

            BXMasTopSnackbar(
                snackbarMessage = currentMessage,
                isVisible = isVisible,
                onDismiss = { topSnackbarMessages.remove(currentMessage) }
            )

            LaunchedEffect(currentMessage) {
                delay(currentMessage.durationMillis)
                if (isVisible.value) {
                    isVisible.value = false
                    topSnackbarMessages.remove(currentMessage)
                }
            }
        }

        // Overlay Loader
        if (showLoader) {
            DiscogsLoaderOverlay {
                DiscogsLoadingIndicator()
            }
        }

    }
}
