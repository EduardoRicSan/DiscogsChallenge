package com.tech.discogschallenge.presentation.navigation.extension

import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import com.tech.core.route.AlbumsByArtist
import com.tech.core.route.ArtistInfoDetail
import com.tech.core.route.DiscogsAppDestination
import com.tech.core.route.SearchArtist
import com.tech.discogschallenge.R
import com.tech.design_system.common.model.DiscogsUiText
import com.tech.discogschallenge.presentation.navigation.extension.toRouteOrNull

// Converts a BXMasAppDestination to a UiText for the top bar title
fun DiscogsAppDestination.toTopBarTitle(): DiscogsUiText =
    when (this) {
        SearchArtist ->  DiscogsUiText.StringRes(R.string.title_search_artist)
        ArtistInfoDetail -> DiscogsUiText.StringRes(R.string.title_artist_info)
        AlbumsByArtist -> DiscogsUiText.StringRes(R.string.title_albums_by_artist)
        else -> DiscogsUiText.StringRes(R.string.app_name)
    }

// Maps a NavBackStackEntry to a BXMasAppDestination
fun NavBackStackEntry.toAppDestination(): DiscogsAppDestination? {
    return when {
        toRouteOrNull<SearchArtist>() != null -> SearchArtist
        toRouteOrNull<ArtistInfoDetail>() != null -> ArtistInfoDetail(artistId = -1)
        toRouteOrNull<AlbumsByArtist>() != null -> AlbumsByArtist(artistId = -1)
        else -> null
    }
}

// Safely attempts to convert the NavBackStackEntry route to a type T
inline fun <reified T : Any> NavBackStackEntry.toRouteOrNull(): T? {
    return runCatching {
        toRoute<T>()
    }.getOrNull()
}