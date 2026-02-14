package com.tech.design_system.components.topBar

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview


/**
 * DiscogsTopBar
 *
 * A customizable TopAppBar following the Discogs design system.
 * Supports optional back button, title, and up to two action icons.
 *
 * @param titleText Text to display as the title
 * @param showBackButton Whether to display the back button
 * @param onBackClick Lambda invoked when back button is clicked
 * @param firstActionIcon Optional first action icon
 * @param showFirstActionIcon Whether to display the first action icon
 * @param onFirstActionClick Lambda invoked when first action icon is clicked
 * @param secondActionIcon Optional second action icon
 * @param showSecondActionIcon Whether to display the second action icon
 * @param onSecondActionClick Lambda invoked when second action icon is clicked
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscogsTopBar(
    titleText: String,
    showBackButton: Boolean = false,
    onBackClick: () -> Unit = {},
    firstActionIcon: ImageVector? = null,
    showFirstActionIcon: Boolean = true,
    onFirstActionClick: () -> Unit = {},
    secondActionIcon: ImageVector? = null,
    showSecondActionIcon: Boolean = true,
    onSecondActionClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = titleText,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        actions = {
            if (firstActionIcon != null && showFirstActionIcon) {
                IconButton(onClick = onFirstActionClick) {
                    Icon(
                        imageVector = firstActionIcon,
                        contentDescription = "First action",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            if (secondActionIcon != null && showSecondActionIcon) {
                IconButton(onClick = onSecondActionClick) {
                    Icon(
                        imageVector = secondActionIcon,
                        contentDescription = "Second action",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.statusBarsPadding()
    )
}

/**
 * Preview for DiscogsTopBar
 */
@Preview(showBackground = true)
@Composable
fun DiscogsTopBarPreview() {
    DiscogsTopBar(
        titleText = "Discogs App",
        showBackButton = true,
        onBackClick = { /* TODO */ },
        firstActionIcon = Icons.Default.Notifications,
        onFirstActionClick = { /* TODO */ },
        secondActionIcon = Icons.AutoMirrored.Filled.Help,
        onSecondActionClick = { /* TODO */ }
    )
}

