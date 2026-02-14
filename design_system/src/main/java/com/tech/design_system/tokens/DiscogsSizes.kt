package com.tech.design_system.tokens

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Centralized size tokens for the Discogs Design System.
 *
 * Includes card heights, avatar sizes, icon sizes, and other component dimensions.
 */
@Immutable
data class DiscogsSizes(
    // Card heights
    val imageCardHeight: Dp = 200.dp,
    val profileCardHeight: Dp = 80.dp,

    // Overlay / gradient heights
    val imageOverlayHeight: Dp = 80.dp,

    // Avatar sizes
    val avatarSmall: Dp = 32.dp,
    val avatarMedium: Dp = 48.dp,
    val avatarLarge: Dp = 60.dp,
    val avatarExtraLarge: Dp = 80.dp,

    // Icon sizes
    val iconSmall: Dp = 16.dp,
    val iconMedium: Dp = 24.dp,
    val iconLarge: Dp = 32.dp,

    //Image Size
    val imageSmall: Dp = 100.dp,
    val imageMedium: Dp = 200.dp,
    val imageLarge: Dp = 300.dp,

)

val LocalSize = staticCompositionLocalOf { DiscogsSizes() }

val MaterialTheme.sizes
    @Composable
    get() = LocalSize.current
