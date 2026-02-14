package com.tech.design_system.common.function

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import kotlin.math.abs

@Composable
fun rememberAlbumColor(albumId: Int): Color {
    val palette = listOf(
        Color(0xFF1DB954),
        Color(0xFFE91E63),
        Color(0xFF3F51B5),
        Color(0xFFFF5722),
        Color(0xFF9C27B0),
        Color(0xFF009688),
        Color(0xFFFF9800)
    )

    return remember(albumId) {
        palette[abs(albumId.hashCode()) % palette.size]
    }
}
