package com.tech.core.route

import kotlinx.serialization.Serializable

// Navigation destinations used across the Discogs app
@Serializable
sealed interface DiscogsAppDestination

// Search artist main screen destination
@Serializable
data object SearchArtist : DiscogsAppDestination

// Artist detail screen destination
@Serializable
data class ArtistInfoDetail(
    val artistId: Int
) : DiscogsAppDestination

// Albums list by artist destination
@Serializable
data class AlbumsByArtist(
    val artistId: Int
) : DiscogsAppDestination
@Serializable
data object AbooutApp : DiscogsAppDestination

