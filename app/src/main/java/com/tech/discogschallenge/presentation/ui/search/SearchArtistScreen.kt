package com.tech.discogschallenge.presentation.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tech.design_system.common.model.DiscogsSnackbarMessage
import com.tech.design_system.common.model.DiscogsSnackbarType
import com.tech.design_system.components.loader.DiscogsLoadingIndicator
import com.tech.design_system.components.searchBar.DiscogsSimpleSearchBar
import com.tech.design_system.tokens.spacing
import com.tech.discogschallenge.R
import com.tech.discogschallenge.presentation.viewmodel.search.SearchArtistIntent
import com.tech.discogschallenge.presentation.viewmodel.search.SearchArtistSideEffect
import com.tech.discogschallenge.presentation.viewmodel.search.SearchArtistState
import com.tech.discogschallenge.presentation.viewmodel.search.SearchArtistViewModel

/**
 * Screen responsible for searching artists and displaying results,
 * handling loading, pagination, and error side effects.
 */
@Composable
fun SearchArtistScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchArtistViewModel = hiltViewModel(),
    showTopSnackbar: (DiscogsSnackbarMessage) -> Unit,
    onArtistItemClick: (Int) -> Unit,
) {
    val state by viewModel.container.stateFlow.collectAsState()
    val sideEffectFlow = viewModel.container.sideEffectFlow

    // ---------- SIDE EFFECTS ----------
    LaunchedEffect(sideEffectFlow) {
        sideEffectFlow.collect { effect ->
            when (effect) {
                is SearchArtistSideEffect.ShowError -> {
                    showTopSnackbar(
                        DiscogsSnackbarMessage(
                            message = effect.message,
                            type = DiscogsSnackbarType.Error
                        )
                    )
                }
            }
        }
    }

    Column(modifier = modifier.fillMaxSize()) {

        // ---------- SEARCH INPUT ----------
        SearchBar(state.query, viewModel::onIntent)

        Box(modifier = Modifier.fillMaxSize()) {
            when {

                // Initial loading state
                state.isLoading && state.artists.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        DiscogsLoadingIndicator()
                    }
                }

                // Empty results state
                state.artists.isEmpty() -> {
                    EmptyPlaceholder(
                        stringResource(R.string.title_no_artist_found)
                    )
                }

                // Results list
                else -> {
                    ArtistList(
                        state = state,
                        onLoadNextPage = {
                            viewModel.onIntent(SearchArtistIntent.LoadNextPage)
                        },
                        onArtistItemClick = onArtistItemClick
                    )
                }
            }
        }
    }
}

/**
 * Search input component connected to search intents.
 */
@Composable
private fun SearchBar(
    query: String,
    onIntent: (SearchArtistIntent) -> Unit
) {
    DiscogsSimpleSearchBar(
        query = query,
        onQueryChange = { q -> onIntent(SearchArtistIntent.Search(q)) },
        onSearch = { q -> onIntent(SearchArtistIntent.Search(q)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.lg)
    )
}



/**
 * Displays paginated list of artists with automatic next page loading.
 */
@Composable
private fun ArtistList(
    state: SearchArtistState,
    onLoadNextPage: () -> Unit,
    onArtistItemClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(MaterialTheme.spacing.lg),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
    ) {

        // Artist items
        items(state.artists) { artist ->
            ArtistItem(artist, onArtistItemClick)
        }

        // Pagination loader
        if (state.isLoading && state.artists.isNotEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = MaterialTheme.spacing.md),
                    contentAlignment = Alignment.Center
                ) {
                    DiscogsLoadingIndicator()
                }
            }
        }

        // Trigger next page loading
        if (!state.endReached && !state.isLoading) {
            item {
                LaunchedEffect(Unit) { onLoadNextPage() }
            }
        }
    }
}
