package com.tech.design_system.components.loader

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.LoadingIndicatorDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tech.design_system.theme.DiscogsChallengeTheme
import com.tech.design_system.tokens.spacing


/**
 * A simple circular loading indicator with optional close action.
 *
 * Uses an infinite animation to indicate progress in a lightweight manner.
 *
 * @param onClose Optional callback invoked when the indicator should be closed.
 *
 * Example usage:
 * DiscogsSimpleLoadingIndicator(
 *     onClose = { /* handle close */ }
 * )
 */

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DiscogsSimpleLoadingIndicator(
    onClose: (() -> Unit)? = null
) {
    val transition = rememberInfiniteTransition()
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Restart
        )
    )
    LoadingIndicator(progress = { progress }, modifier = Modifier.size(60.dp))
}

/**
 * A customizable indeterminate loading indicator with polygon animation.
 *
 * Uses Material3 Expressive LoadingIndicator with infinite animation. You can
 * customize color, size, and provide an optional close callback.
 *
 * @param color Color of the loading indicator. Defaults to [MaterialTheme.colorScheme.primary].
 * @param onClose Optional callback invoked when the indicator should be closed.
 *
 * Example usage:
 * DiscogsLoadingIndicator(
 *     color = MaterialTheme.colorScheme.secondary,
 *     onClose = { /* handle close */ }
 * )
 */
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun DiscogsLoadingIndicator(
    color: Color = MaterialTheme.colorScheme.primary,
    onClose: (() -> Unit)? = null
) {
    val transition = rememberInfiniteTransition()
    val progress by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500),
            repeatMode = RepeatMode.Restart
        )
    )
    LoadingIndicator(
        progress = { progress },
        color = color,
        polygons = LoadingIndicatorDefaults.IndeterminateIndicatorPolygons,
        modifier = Modifier.size(80.dp)
    )
}


@Preview(showBackground = true)
@Composable
private fun DiscogsSimpleLoadingIndicatorPreview() {
    DiscogsChallengeTheme {
        DiscogsSimpleLoadingIndicator(
            onClose = { /* optional */ }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DiscogsLoadingIndicatorPreview() {
    DiscogsChallengeTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            DiscogsLoadingIndicator(
                color = MaterialTheme.colorScheme.primary,
                onClose = { /* optional */ }
            )
            DiscogsLoadingIndicator(
                color = MaterialTheme.colorScheme.secondary,
                onClose = { /* optional */ }
            )
        }
    }
}
