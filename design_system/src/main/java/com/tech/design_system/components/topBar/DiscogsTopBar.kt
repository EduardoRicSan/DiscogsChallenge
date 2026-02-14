package com.tech.design_system.components.topBar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.tech.core.route.DiscogsAppDestination
import com.tech.core.route.SearchArtist


/**
 * BXMasTopBar
 * Top bar with optional back button, title, and two optional action icons.
 *
 * @param titleText Text to display as the title (can use custom text composable)
 * @param showBackButton Whether to show the back button
 * @param onBackClick Lambda invoked when back button is clicked
 * @param firstActionIcon Optional first action icon (e.g., notifications)
 * @param showFirstActionIcon Whether to display first action icon
 * @param onFirstActionClick Lambda invoked when first action is clicked
 * @param secondActionIcon Optional second action icon (e.g., support)
 * @param showSecondActionIcon Whether to display second action icon
 * @param onSecondActionClick Lambda invoked when second action is clicked
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DiscogsTopBar(
    titleText: String,
    currentRoute: DiscogsAppDestination,
    onBackClick: () -> Unit = {},
    onBackLongPress: () -> Unit = {},
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
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .then(
                        if (currentRoute is SearchArtist) {
                            Modifier.combinedClickable(
                                onClick = {},
                                onLongClick = onBackLongPress
                            )
                        } else {
                            Modifier.clickable { onBackClick() }
                        }
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
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
        onBackClick = { /* TODO */ },
        currentRoute = SearchArtist,
        onBackLongPress = { /* TODO */ }
    )
}

