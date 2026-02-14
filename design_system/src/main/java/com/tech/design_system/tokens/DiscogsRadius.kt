package com.tech.design_system.tokens

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class DiscogsRadius(
    val small: Dp = 8.dp,
    val medium: Dp = 12.dp,
    val large: Dp = 20.dp
)

val LocalRadius = staticCompositionLocalOf { DiscogsRadius() }

val MaterialTheme.radius
    @Composable
    get() = LocalRadius.current
