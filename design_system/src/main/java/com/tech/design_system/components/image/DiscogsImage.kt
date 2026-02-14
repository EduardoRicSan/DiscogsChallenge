package com.tech.design_system.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.tech.design_system.common.model.DiscogsImageSource
import com.tech.design_system.common.viewmodel.CoilViewModel
import com.tech.design_system.theme.DiscogsChallengeTheme
import com.tech.design_system.tokens.sizes
import com.tech.design_system.tokens.spacing

/**
 * A reusable image composable supporting URL, resource, or Bitmap sources.
 *
 * Provides optional click handling and supports placeholders and error images.
 *
 * @param source The image source ([DiscogsImageSource.Url], [DiscogsImageSource.Resource],
 * or [DiscogsImageSource.Bitmap]).
 * @param modifier Modifier applied to the image container.
 * @param contentDescription Accessibility description for screen readers.
 * @param contentScale How the image should scale to fit its bounds. Default is [ContentScale.Crop].
 * @param placeholder Optional placeholder painter displayed while loading.
 * @param error Optional painter displayed if image loading fails.
 * @param onClick Optional callback invoked when the image is clicked.
 */
@Composable
fun DiscogsImage(
    source: DiscogsImageSource,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop,
    placeholder: Painter? = null,
    error: Painter? = null,
    onClick: (() -> Unit)? = null,
) {
    val clickableModifier =
        if (onClick != null) modifier.clickable(onClick = onClick)
        else modifier

    when (source) {
        is DiscogsImageSource.Url -> {
            AsyncImage(
                model = source.url,
                contentDescription = contentDescription,
                modifier = clickableModifier,
                contentScale = contentScale,
                placeholder = placeholder,
                error = error
            )
        }

        is DiscogsImageSource.Resource -> {
            Image(
                painter = painterResource(source.resId),
                contentDescription = contentDescription,
                modifier = clickableModifier,
                contentScale = contentScale
            )
        }

        is DiscogsImageSource.Bitmap -> {
            Image(
                bitmap = source.imageBitmap,
                contentDescription = contentDescription,
                modifier = clickableModifier,
                contentScale = contentScale
            )
        }
    }
}

/**
 * A circular image composable with optional border and click handling.
 *
 * Uses [DiscogsImage] internally and provides standardized avatar sizes
 * via the Design System.
 *
 * @param source The image source ([DiscogsImageSource.Url], [DiscogsImageSource.Resource],
 * or [DiscogsImageSource.Bitmap]).
 * @param contentDescription Accessibility description for screen readers.
 * @param size Diameter of the circular image. Defaults to [MaterialTheme.sizes.avatarMedium].
 * @param borderWidth Width of the circular border.
 * @param borderColor Color of the border. Defaults to [MaterialTheme.colorScheme.outline].
 * @param onClick Optional callback invoked when the image is clicked.
 */

@Composable
fun DiscogsCircularImage(
    source: DiscogsImageSource,
    contentDescription: String? = null,
    size: Dp = MaterialTheme.sizes.avatarMedium,
    borderWidth: Dp = 1.dp,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    onClick: (() -> Unit)? = null
) {
    DiscogsImage(
        source = source,
        contentDescription = contentDescription,
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .border(borderWidth, borderColor, CircleShape)
            .let {
                if (onClick != null) it.clickable(onClick = onClick) else it
            }
    )
}

@Preview(showBackground = true)
@Composable
private fun DiscogsImageResourcePreview() {
    DiscogsChallengeTheme {
        DiscogsImage(
            source = DiscogsImageSource.Resource(android.R.drawable.sym_def_app_icon),
            contentDescription = "Resource Image"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DiscogsCircularImagePreview() {
    DiscogsChallengeTheme {
        var clicked by remember { mutableStateOf(false) }

        DiscogsCircularImage(
            source = DiscogsImageSource.Resource(android.R.drawable.sym_def_app_icon),
            size = MaterialTheme.sizes.avatarLarge,
            borderColor = if (clicked) Color.Blue else MaterialTheme.colorScheme.outline,
            onClick = { clicked = !clicked },
            contentDescription = "Circular Avatar"
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DiscogsCircularImageRowPreview() {
    DiscogsChallengeTheme {
        var clicked by remember { mutableStateOf(false) }

        Row {
            DiscogsCircularImage(
                source = DiscogsImageSource.Resource(android.R.drawable.sym_def_app_icon),
                size = MaterialTheme.sizes.avatarSmall,
                onClick = { clicked = !clicked },
                contentDescription = "Small Avatar"
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.md))
            DiscogsCircularImage(
                source = DiscogsImageSource.Resource(android.R.drawable.sym_def_app_icon),
                size = MaterialTheme.sizes.avatarMedium,
                onClick = { clicked = !clicked },
                contentDescription = "Medium Avatar"
            )
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.md))
            DiscogsCircularImage(
                source = DiscogsImageSource.Resource(android.R.drawable.sym_def_app_icon),
                size = MaterialTheme.sizes.avatarExtraLarge,
                onClick = { clicked = !clicked },
                contentDescription = "Extra Large Avatar"
            )
        }
    }
}

