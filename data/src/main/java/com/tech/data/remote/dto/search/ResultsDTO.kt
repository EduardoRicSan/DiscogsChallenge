package com.tech.data.remote.dto.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
