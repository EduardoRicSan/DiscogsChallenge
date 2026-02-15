package com.tech.domain.model.getArtistInfo

data class Artist(
    val id: Int,
    val name: String,
    val resourceUrl: String?,
    val uri: String?,
    val profile: String?,
    val images: List<Image>,
    val members: List<Member>,
    val urls: List<String>,
    val nameVariations: List<String>,
    val dataQuality: String?,
)

data class Image(
    val type: String?,
    val uri: String?,
    val resourceUrl: String?,
    val uri150: String?,
    val width: Int?,
    val height: Int?,
)

data class Member(
    val id: Int?,
    val name: String?,
    val resourceUrl: String?,
    val active: Boolean?
)

data class ArtistFull(
    val id: Int,
    val name: String,
    val title: String?,
    val thumb: String?,
    val images: List<Image>,
    val members: List<Member>,
    val resourceUrl: String?,
    val profile: String?,
    val urls: List<String>,
    val nameVariations: List<String>,
    val dataQuality: String?
)

