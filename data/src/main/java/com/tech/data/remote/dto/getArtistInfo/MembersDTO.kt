package com.tech.data.remote.dto.getArtistInfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MembersDTO(
    @SerialName("id"           ) var id          : Int?     = null,
    @SerialName("name"         ) var name        : String?  = null,
    @SerialName("resource_url" ) var resourceUrl : String?  = null,
    @SerialName("active"       ) var active      : Boolean? = null,
)
