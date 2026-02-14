package com.tech.data.remote.dto.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscogsUrlsDTO (
    @SerialName("last" ) var last : String? = null,
    @SerialName("next" ) var next : String? = null,
)
