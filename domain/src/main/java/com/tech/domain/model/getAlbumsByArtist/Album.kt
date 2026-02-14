package com.tech.domain.model.getAlbumsByArtist

// Album.kt
data class Album(
    val id: Int,
    val title: String?,
    val year: Int?,
    val thumb: String?,
    val format: String?,
    val type: String?,
    val label: String?,
    val artist: String?,
)
