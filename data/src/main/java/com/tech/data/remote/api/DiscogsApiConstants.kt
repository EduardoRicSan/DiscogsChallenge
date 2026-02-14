package com.tech.data.remote.api

import com.tech.data.BuildConfig

internal object DiscogsApiConstants {

    // Endpoint to search artists in Discogs database
    const val DISCOGS_SEARCH_ENDPOINT = "/database/search"

    // Endpoint base to retrieve artist information
    const val DISCOGS_GET_ARTIST_INFO_ENDPOINT = "/artists/"

    // Endpoint to fetch artist releases/albums
    const val DISCOGS_GET_ARTIST_ALBUMS_ENDPOINT = "/releases"
}

internal object DiscogsApiQueryParameters {

    // Authorization header name
    const val DISCOGS_API_KEY_PARAMETER = "Authorization"

    // Authorization header value using Discogs token
    const val DISCOGS_API_KEY_PARAMETER_VALUE =
        "Discogs token ${BuildConfig.DISCOGS_API_KEY}"

    // Search query text parameter
    const val DISCOGS_SEARCH_QUERY_PARAMETER = "q"

    // Search filter type parameter
    const val DISCOGS_SEARCH_TYPE_PARAMETER = "type"

    // Fixed value to search only artists
    const val DISCOGS_SEARCH_TYPE_VALUE = "artist"

    // Pagination size parameter
    const val DISCOGS_SEARCH_PER_PAGE_PARAMETER = "per_page"

    // Default results per page
    const val DISCOGS_SEARCH_PER_PAGE_VALUE = 30

    // Pagination page parameter
    const val DISCOGS_SEARCH_PAGE_PARAMETER = "page"
}

internal object RemoteDataModuleConstants {

    // Tag used for Discogs HTTP client logging
    const val LOG_DISCOGS_CLIENT = "DiscogsClient"
}
