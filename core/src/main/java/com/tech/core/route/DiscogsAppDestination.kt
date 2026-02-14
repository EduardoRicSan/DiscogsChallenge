package com.tech.core.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface DiscogsAppDestination

@Serializable
data object SearchArtist : DiscogsAppDestination

@Serializable
data class ArtistInfoDetail(
    val artistId: Int
) : DiscogsAppDestination
@Serializable
data class AlbumsByArtist(
    val artistId: Int
) : DiscogsAppDestination
