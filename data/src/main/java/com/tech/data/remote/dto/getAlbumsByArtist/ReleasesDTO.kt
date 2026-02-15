package com.tech.data.remote.dto.getAlbumsByArtist

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReleasesDTO (
    @SerialName("id"           ) var id          : Int?    = null,
    @SerialName("status"       ) var status      : String? = null,
    @SerialName("type"         ) var type        : String? = null,
    @SerialName("format"       ) var format      : String? = null,
    @SerialName("label"        ) var label       : String? = null,
    @SerialName("title"        ) var title       : String? = null,
    @SerialName("resource_url" ) var resourceUrl : String? = null,
    @SerialName("role"         ) var role        : String? = null,
    @SerialName("artist"       ) var artist      : String? = null,
    @SerialName("year"         ) var year        : Int?    = null,
    @SerialName("thumb"        ) var thumb       : String? = null,
    @SerialName("stats"        ) var stats       : StatsDTO?  = StatsDTO(),
)
