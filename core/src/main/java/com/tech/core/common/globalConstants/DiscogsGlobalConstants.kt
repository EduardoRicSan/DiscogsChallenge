package com.tech.core.common.globalConstants

import com.tech.core.common.globalConstants.DiscogsGlobalConstants.SERVER_OK_BASE_URL
import com.tech.core.common.globalConstants.DiscogsGlobalConstants.SERVER_OK_BASE_URL_IMAGE_SIZE

// Global constants used across Discogs feature
object DiscogsGlobalConstants {
    const val DELAY_DEBOUNCE = 500L          // Search debounce delay (ms)
    const val PAGE_INITIAL_VALUE = 1         // Default pagination start page
    const val UNKNOWN_ERROR_MESSAGE = "Unknown error" // Fallback error message
    const val ZERO_VALUE = 0                 // Default numeric fallback
    const val SERVER_OK_BASE_URL = "https://picsum.photos/seed/" // Dummy image base URL
    const val SERVER_OK_BASE_URL_IMAGE_SIZE = "600/400"          // Dummy image size
}

// Generates a fallback random image URL based on an id
fun randomPhotoUrl(id: Int): String {
    return "$SERVER_OK_BASE_URL$id/$SERVER_OK_BASE_URL_IMAGE_SIZE"
}



