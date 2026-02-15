package com.tech.design_system.components.box

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

/**
 * A reusable full-screen overlay composable typically used to display loading
 * indicators or blocking UI content on top of the current screen.
 *
 * This component renders a semi-transparent background that prevents user
 * interaction with underlying content while displaying a centered composable.
 *
 * @param modifier Modifier applied to the overlay container.
 * @param backgroundColor Background color used to dim the underlying UI.
 * @param content Composable content displayed at the center of the overlay.
 *
 * Example usage:
 * DiscogsLoaderOverlay {
 *     CircularProgressIndicator()
 * }
 */
@Composable
fun DiscogsLoaderOverlay(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Black.copy(alpha = 0.4f),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun DiscogsLoaderOverlayPreview() {
    DiscogsLoaderOverlay {
        CircularProgressIndicator()
    }
}

