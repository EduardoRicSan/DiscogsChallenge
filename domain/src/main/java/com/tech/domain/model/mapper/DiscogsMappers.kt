package com.tech.domain.model.mapper

import com.tech.data.remote.dto.getAlbumsByArtist.ReleasesDTO
import com.tech.data.remote.dto.getArtistInfo.GetArtistInfoResponseDTO
import com.tech.data.remote.dto.getArtistInfo.ImagesDTO
import com.tech.data.remote.dto.getArtistInfo.MembersDTO
import com.tech.data.remote.dto.search.ResultsDTO
import com.tech.domain.model.getAlbumsByArtist.Album
import com.tech.domain.model.getArtistInfo.Artist
import com.tech.domain.model.getArtistInfo.Image
import com.tech.domain.model.getArtistInfo.Member
import com.tech.domain.model.search.SearchArtistResult

fun ResultsDTO.toDomain(): SearchArtistResult =
    SearchArtistResult(
        id = id ?: 0,
        title = title,
        type = type,
        thumb = thumb,
        coverImage = coverImage,
    )

fun ImagesDTO.toDomain(): Image =
    Image(
        type = type,
        uri = uri,
        resourceUrl = resourceUrl,
        uri150 = uri150,
        width = width,
        height = height,
    )

fun MembersDTO.toDomain(): Member =
    Member(
        id = id,
        name = name,
        resourceUrl = resourceUrl,
        active = active,
    )

fun GetArtistInfoResponseDTO.toDomain(): Artist =
    Artist(
        id = id ?: 0,
        name = name.orEmpty(),
        resourceUrl = resourceUrl,
        uri = uri,
        profile = profile,
        images = images.map { it.toDomain() },
        members = members.map { it.toDomain() },
        urls = urls,
        nameVariations = namevariations,
        dataQuality = dataQuality,
    )

fun ReleasesDTO.toDomain(): Album =
    Album(
        id = id ?: 0,
        title = title,
        year = year,
        thumb = thumb,
        format = format,
        type = type,
        label = label,
        artist = artist,
    )
