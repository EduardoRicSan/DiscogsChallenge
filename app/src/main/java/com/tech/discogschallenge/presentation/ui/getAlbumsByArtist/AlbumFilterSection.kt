package com.tech.discogschallenge.presentation.ui.getAlbumsByArtist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tech.design_system.components.chip.DiscogsFilterChip
import com.tech.design_system.components.text.DiscogsOverlineText
import com.tech.design_system.tokens.spacing
import com.tech.discogschallenge.R
import com.tech.discogschallenge.presentation.viewmodel.getAlbumsByArtist.AlbumsByArtistIntent
import com.tech.discogschallenge.presentation.viewmodel.getAlbumsByArtist.AlbumsByArtistState

/**
 * Displays filtering options for albums (year and label).
 * Selecting a chip toggles the active filter.
 */
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

            // Section title
            DiscogsOverlineText(
                text = stringResource(R.string.title_filter_by_year),
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.sm,
                    vertical = MaterialTheme.spacing.sm
                )
            )

            // Horizontal list of year chips
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

            // Section title
            DiscogsOverlineText(
                text = stringResource(R.string.title_filter_by_label),
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.sm,
                    vertical = MaterialTheme.spacing.sm
                )
            )

            // Horizontal list of label chips
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
