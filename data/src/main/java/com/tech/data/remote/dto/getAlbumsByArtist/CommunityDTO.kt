package com.tech.data.remote.dto.getAlbumsByArtist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommunityDTO(
    @SerialName("in_wantlist"   ) var inWantlist   : Int? = null,
    @SerialName("in_collection" ) var inCollection : Int? = null,
)
