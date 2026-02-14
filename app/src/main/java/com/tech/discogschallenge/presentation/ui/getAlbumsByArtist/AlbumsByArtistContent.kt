package com.tech.discogschallenge.presentation.ui.getAlbumsByArtist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.tech.design_system.R
import com.tech.design_system.common.function.rememberAlbumColor
import com.tech.design_system.common.model.DiscogsImageSource
import com.tech.design_system.components.card.DiscogsCard
import com.tech.design_system.components.chip.DiscogsFilterChip
import com.tech.design_system.components.image.DiscogsImage
import com.tech.design_system.components.text.DiscogsBodyText
import com.tech.design_system.components.text.DiscogsLabelText
import com.tech.design_system.components.text.DiscogsOverlineText
import com.tech.design_system.tokens.spacing
import com.tech.discogschallenge.presentation.viewmodel.getAlbumsByArtist.AlbumsByArtistIntent
import com.tech.discogschallenge.presentation.viewmodel.getAlbumsByArtist.AlbumsByArtistState
import com.tech.domain.model.getAlbumsByArtist.Album

@Composable
fun AlbumsByArtistContent(
    state: AlbumsByArtistState,
    albums: List<Album>,
    availableYears: List<Int>,
    availableLabels: List<String>,
    onIntent: (AlbumsByArtistIntent) -> Unit
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(MaterialTheme.spacing.lg),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
    ) {

        item(span = { GridItemSpan(maxLineSpan) }) {
            AlbumsFiltersSection(
                state = state,
                years = availableYears,
                labels = availableLabels,
                onIntent = onIntent
            )
        }

        items(
            items = albums,
            key = { it.id }
        ) { album ->
            AlbumGridItem(album)
        }
    }
}

@Composable
fun AlbumsFiltersSection(
    state: AlbumsByArtistState,
    years: List<Int>,
    labels: List<String>,
    onIntent: (AlbumsByArtistIntent) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = MaterialTheme.spacing.lg)
    ) {

        // ---------- YEAR FILTER ----------
        if (years.isNotEmpty()) {

            DiscogsOverlineText(
                text = "Filter by Year",
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.sm,
                    vertical = MaterialTheme.spacing.sm
                )
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.sm)
            ) {
                items(
                    items = years,
                    key = { it }
                ) { year ->

                    DiscogsFilterChip(
                        selected = state.selectedYear == year,
                        text = year.toString(),
                        onClick = {
                            onIntent(
                                AlbumsByArtistIntent.FilterByYear(
                                    if (state.selectedYear == year) null else year
                                )
                            )
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(MaterialTheme.spacing.md))

        // ---------- LABEL FILTER ----------
        if (labels.isNotEmpty()) {

            DiscogsOverlineText(
                text = "Filter by Label",
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.sm,
                    vertical = MaterialTheme.spacing.sm
                )
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.sm)
            ) {
                items(
                    items = labels,
                    key = { it }
                ) { label ->

                    DiscogsFilterChip(
                        selected = state.selectedLabel == label,
                        text = label,
                        onClick = {
                            onIntent(
                                AlbumsByArtistIntent.FilterByLabel(
                                    if (state.selectedLabel == label) null else label
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AlbumGridItem(
    album: Album
) {

    val baseColor = rememberAlbumColor(album.id)

    DiscogsCard(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentPadding = PaddingValues(MaterialTheme.spacing.none)
    ) {

        Box {
            // ---------- BACKGROUND COLOR ----------
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(baseColor)
            )

            // ---------- BACKGROUND IMAGE ----------
            DiscogsImage(
                source = DiscogsImageSource.Resource(R.drawable.ic_music_album),
                contentDescription = album.title,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.35f),
                contentScale = ContentScale.Crop
            )
            // ---------- DARK GRADIENT OVERLAY ----------
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black.copy(alpha = 0.85f)
                            )
                        )
                    )
            )

            // ---------- CONTENT ----------
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(MaterialTheme.spacing.md)
            ) {

                DiscogsBodyText(
                    text = album.title.orEmpty(),
                    color = Color.White,
                    maxLines = 2
                )

                album.year?.let {
                    DiscogsLabelText(
                        text = it.toString(),
                        color = Color.White.copy(alpha = 0.85f)
                    )
                }

                album.label?.let {
                    DiscogsOverlineText(
                        text = it,
                        color = Color.White.copy(alpha = 0.7f),
                        maxLines = 1
                    )
                }
            }
        }
    }
}



