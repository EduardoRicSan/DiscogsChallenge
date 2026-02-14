package com.tech.data.remote.dto.getAlbumsByArtist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatsDTO(
    @SerialName("community" ) var community : CommunityDTO? = CommunityDTO(),
)
