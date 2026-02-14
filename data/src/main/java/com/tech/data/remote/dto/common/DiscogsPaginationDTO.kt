package com.tech.data.remote.dto.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscogsPaginationDTO(
    @SerialName("page"     ) var page    : Int?  = null,
    @SerialName("pages"    ) var pages   : Int?  = null,
    @SerialName("per_page" ) var perPage : Int?  = null,
    @SerialName("items"    ) var items   : Int?  = null,
    @SerialName("urls"     ) var urls    : DiscogsUrlsDTO? = DiscogsUrlsDTO(),
)
