package com.tech.data.remote.dto.getArtistInfo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetArtistInfoResponseDTO(
    @SerialName("name"           ) var name           : String?            = null,
    @SerialName("id"             ) var id             : Int?               = null,
    @SerialName("resource_url"   ) var resourceUrl    : String?            = null,
    @SerialName("uri"            ) var uri            : String?            = null,
    @SerialName("releases_url"   ) var releasesUrl    : String?            = null,
    @SerialName("images"         ) var images         : List<ImagesDTO>  = listOf(),
    @SerialName("profile"        ) var profile        : String?            = null,
    @SerialName("urls"           ) var urls           : List<String>  = listOf(),
    @SerialName("namevariations" ) var namevariations : List<String>  = listOf(),
    @SerialName("members"        ) var members        : List<MembersDTO> = listOf(),
    @SerialName("data_quality"   ) var dataQuality    : String?            = null,
)
