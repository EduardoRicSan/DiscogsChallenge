package com.tech.discogschallenge.presentation.navigation.extension

import androidx.navigation.NavBackStackEntry
import androidx.navigation.toRoute
import com.tech.core.route.AlbumsByArtist
import com.tech.core.route.ArtistInfoDetail
import com.tech.core.route.DiscogsAppDestination
import com.tech.core.route.SearchArtist
import com.tech.discogschallenge.R
import com.tech.design_system.common.model.DiscogsUiText

// Converts a DiscogsAppDestination to a DiscogsUiText for the top bar title
fun DiscogsAppDestination.toTopBarTitle(): DiscogsUiText =
    when (this) {
        is SearchArtist ->  DiscogsUiText.StringRes(R.string.title_search_artist)
        is ArtistInfoDetail -> DiscogsUiText.StringRes(R.string.title_artist_info)
        is AlbumsByArtist -> DiscogsUiText.StringRes(R.string.title_albums_by_artist)
    }


// Safely attempts to convert the NavBackStackEntry route to a type T
inline fun <reified T : Any> NavBackStackEntry.toRouteOrNull(): T? {
    return runCatching {
        toRoute<T>()
    }.getOrNull()
}
