
package com.tech.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DiscogsArtistResponseDTO(
    @SerialName("pagination") val pagination: DiscogsPaginationDTO?,
    @SerialName("results") val results: List<ResultsDTO> = listOf(),
)
@Serializable
data class DiscogsPaginationDTO(
    @SerialName("page"     ) var page    : Int?  = null,
    @SerialName("pages"    ) var pages   : Int?  = null,
    @SerialName("per_page" ) var perPage : Int?  = null,
    @SerialName("items"    ) var items   : Int?  = null,
    @SerialName("urls"     ) var urls    : UrlsDTO? = UrlsDTO(),
)
@Serializable
data class UrlsDTO (
    @SerialName("last" ) var last : String? = null,
    @SerialName("next" ) var next : String? = null,
)
@Serializable
data class ResultsDTO (
    @SerialName("id"           ) var id          : Int?    = null,
    @SerialName("type"         ) var type        : String? = null,
    @SerialName("master_id"    ) var masterId    : String? = null,
    @SerialName("master_url"   ) var masterUrl   : String? = null,
    @SerialName("uri"          ) var uri         : String? = null,
    @SerialName("title"        ) var title       : String? = null,
    @SerialName("thumb"        ) var thumb       : String? = null,
    @SerialName("cover_image"  ) var coverImage  : String? = null,
    @SerialName("resource_url" ) var resourceUrl : String? = null,
)
