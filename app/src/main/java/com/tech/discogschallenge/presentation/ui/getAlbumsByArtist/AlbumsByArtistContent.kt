package com.tech.discogschallenge.presentation.ui.getAlbumsByArtist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tech.design_system.tokens.spacing
import com.tech.discogschallenge.presentation.viewmodel.getAlbumsByArtist.AlbumsByArtistIntent
import com.tech.discogschallenge.presentation.viewmodel.getAlbumsByArtist.AlbumsByArtistState
import com.tech.domain.model.getAlbumsByArtist.Album

const val GRID_CELL_COUNT = 2

/**
 * Displays albums in a 2-column grid along with filter controls.
 * Filters are rendered as a full-width header item.
 */
@Composable
fun AlbumsByArtistContent(
    state: AlbumsByArtistState,
    albums: List<Album>,
    availableYears: List<Int>,
    availableLabels: List<String>,
    onIntent: (AlbumsByArtistIntent) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(GRID_CELL_COUNT),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(MaterialTheme.spacing.lg),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
    ) {

        // Filters section spans full grid width
        item(span = { GridItemSpan(maxLineSpan) }) {
            AlbumsFiltersSection(
                state = state,
                years = availableYears,
                labels = availableLabels,
                onIntent = onIntent
            )
        }

        // Album grid items
        items(
            items = albums,
            key = { it.id }
        ) { album ->
            AlbumGridItem(album)
        }
    }
}
