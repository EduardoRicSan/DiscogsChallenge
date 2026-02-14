package com.tech.data.remote.dto.getArtistInfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesDTO(
    @SerialName("type"         ) var type        : String? = null,
    @SerialName("uri"          ) var uri         : String? = null,
    @SerialName("resource_url" ) var resourceUrl : String? = null,
    @SerialName("uri150"       ) var uri150      : String? = null,
    @SerialName("width"        ) var width       : Int?    = null,
    @SerialName("height"       ) var height      : Int?    = null,
)
