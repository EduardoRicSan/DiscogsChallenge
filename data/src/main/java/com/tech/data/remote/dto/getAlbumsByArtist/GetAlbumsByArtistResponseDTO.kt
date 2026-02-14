package com.tech.data.remote.dto.getAlbumsByArtist

import com.tech.data.remote.dto.common.DiscogsPaginationDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetAlbumsByArtistResponseDTO(
    @SerialName("pagination")
    var pagination: DiscogsPaginationDTO? = DiscogsPaginationDTO(),
    @SerialName("releases") var releases: List<ReleasesDTO> = listOf(),
)
