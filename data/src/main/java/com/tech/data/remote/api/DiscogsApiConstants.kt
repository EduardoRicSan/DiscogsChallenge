package com.tech.data.remote.api

import com.tech.data.BuildConfig

internal object DiscogsApiConstants {
    const val DISCOGS_SEARCH_ENDPOINT = "/database/search"

}

internal object DiscogsApiQueryParameters {
    const val DISCOGS_API_KEY_PARAMETER = "Authorization"
    const val DISCOGS_API_KEY_PARAMETER_VALUE = "Discogs token ${BuildConfig.DISCOGS_API_KEY}"
    const val DISCOGS_SEARCH_QUERY_PARAMETER = "q"
    const val DISCOGS_SEARCH_TYPE_PARAMETER = "type"
    const val DISCOGS_SEARCH_TYPE_VALUE = "artist"
    const val DISCOGS_SEARCH_PER_PAGE_PARAMETER = "per_page"
    const val DISCOGS_SEARCH_PER_PAGE_VALUE = 30
    const val DISCOGS_SEARCH_PAGE_PARAMETER = "page"

}

internal object RemoteDataModuleConstants {
    const val LOG_DISCOGS_CLIENT = "DiscogsClient"
}
