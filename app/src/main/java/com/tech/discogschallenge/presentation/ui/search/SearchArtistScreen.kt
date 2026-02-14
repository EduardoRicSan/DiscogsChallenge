package com.tech.discogschallenge.presentation.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.tech.design_system.common.model.DiscogsImageSource
import com.tech.design_system.common.model.DiscogsSnackbarMessage
import com.tech.design_system.common.model.DiscogsSnackbarType
import com.tech.design_system.components.card.DiscogsCard
import com.tech.design_system.components.image.DiscogsCircularImage
import com.tech.design_system.components.loader.DiscogsLoadingIndicator
import com.tech.design_system.components.searchBar.DiscogsSimpleSearchBar
import com.tech.design_system.components.text.DiscogsTextPlaceholder
import com.tech.design_system.components.text.DiscogsTitleText
import com.tech.design_system.tokens.sizes
import com.tech.design_system.tokens.spacing
import com.tech.discogschallenge.presentation.viewmodel.search.SearchArtistIntent
import com.tech.discogschallenge.presentation.viewmodel.search.SearchArtistSideEffect
import com.tech.discogschallenge.presentation.viewmodel.search.SearchArtistState
import com.tech.discogschallenge.presentation.viewmodel.search.SearchArtistViewModel
import com.tech.domain.model.search.SearchArtistResult

@Composable
fun SearchArtistScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchArtistViewModel = hiltViewModel(),
    showTopSnackbar: (DiscogsSnackbarMessage) -> Unit,
    onArtistItemClick: (Int) -> Unit,
) {
    val state by viewModel.container.stateFlow.collectAsState()
    val sideEffectFlow = viewModel.container.sideEffectFlow

    // Escuchar side effects
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
        SearchBar(state.query, viewModel::onIntent)

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                state.isLoading && state.artists.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        DiscogsLoadingIndicator()
                    }
                }
                state.artists.isEmpty() -> {
                    EmptyPlaceholder()
                }
                else -> {
                    ArtistList(
                        state = state,
                        onLoadNextPage = { viewModel.onIntent(SearchArtistIntent.LoadNextPage) },
                        onArtistItemClick = onArtistItemClick
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchBar(query: String, onIntent: (SearchArtistIntent) -> Unit) {
    DiscogsSimpleSearchBar(
        query = query,
        onQueryChange = { q -> onIntent(SearchArtistIntent.Search(q)) },
        onSearch = { q -> onIntent(SearchArtistIntent.Search(q)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.lg)
    )
}

@Composable
private fun EmptyPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        DiscogsTextPlaceholder(text = "No artists found")
    }
}

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
        items(state.artists) { artist ->
            ArtistItem(artist, onArtistItemClick)
        }

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

        if (!state.endReached && !state.isLoading) {
            item {
                LaunchedEffect(Unit) { onLoadNextPage() }
            }
        }
    }
}

@Composable
private fun ArtistItem(
    artist: SearchArtistResult,
    onArtistItemClick: (Int) -> Unit,
    ) {
    DiscogsCard(
        onClick = { onArtistItemClick(artist.id) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            DiscogsCircularImage(
                source = DiscogsImageSource.Url(artist.thumb ?: ""),
                size = MaterialTheme.sizes.avatarLarge
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.md))
            Column(modifier = Modifier.fillMaxWidth()) {
                DiscogsTitleText(text = artist.title.orEmpty())
            }
        }
    }
}