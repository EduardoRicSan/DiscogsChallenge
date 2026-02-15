package com.tech.design_system.common.model

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Unified model of Image source
 */
sealed class DiscogsImageSource {
    data class Url(val url: String) : DiscogsImageSource()
    data class Resource(val resId: Int) : DiscogsImageSource()
    data class Bitmap(val imageBitmap: ImageBitmap) : DiscogsImageSource()
}
