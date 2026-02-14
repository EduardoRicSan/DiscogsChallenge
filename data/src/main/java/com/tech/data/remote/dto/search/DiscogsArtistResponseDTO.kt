package com.tech.data.remote.dto.search

import com.tech.data.remote.dto.common.DiscogsPaginationDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscogsArtistResponseDTO(
    @SerialName("pagination") val pagination: DiscogsPaginationDTO? = null,
    @SerialName("results") val results: List<ResultsDTO> = listOf(),
)
