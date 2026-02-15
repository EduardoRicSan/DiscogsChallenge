package com.tech.discogschallenge.presentation.ui.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tech.design_system.components.text.DiscogsTextPlaceholder
import com.tech.discogschallenge.R

/**
 * Placeholder shown when no artists are found.
 */
@Composable
fun EmptyPlaceholder(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DiscogsTextPlaceholder(text = message)
    }
}
