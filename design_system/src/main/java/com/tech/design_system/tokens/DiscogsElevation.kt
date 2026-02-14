package com.tech.design_system.tokens

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class DiscogsElevation(
    val level0: Dp = 0.dp,
    val level1: Dp = 2.dp,
    val level2: Dp = 4.dp,
    val level3: Dp = 8.dp
)

val LocalElevation = staticCompositionLocalOf { DiscogsElevation() }

val MaterialTheme.elevation
    @Composable
    get() = LocalElevation.current
