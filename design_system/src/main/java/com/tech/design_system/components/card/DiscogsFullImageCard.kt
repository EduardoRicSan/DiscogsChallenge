package com.tech.design_system.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import com.tech.design_system.common.model.DiscogsImageSource
import com.tech.design_system.components.image.DiscogsImage
import com.tech.design_system.components.text.DiscogsBodyText
import com.tech.design_system.components.text.DiscogsHeadlineText
import com.tech.design_system.theme.DiscogsChallengeTheme
import com.tech.design_system.tokens.sizes
import com.tech.design_system.tokens.spacing

/**
 * Card displaying a full-bleed background image with optional title and subtitle.
 * Built on top of [DiscogsCard] to ensure visual consistency across the design system.
 */
@Composable
fun DiscogsFullImageCard(
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    imageRes: Int? = null,
    title: String? = null,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null
) {
    DiscogsCard(
        modifier = modifier
            .fillMaxWidth()
            .height(MaterialTheme.sizes.imageCardHeight),
        onClick = onClick
    ) {
        Box(Modifier.fillMaxSize()) {

            when {
                imageUrl != null -> DiscogsImage(
                    source = DiscogsImageSource.Url(imageUrl),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                imageRes != null -> DiscogsImage(
                    source = DiscogsImageSource.Resource(imageRes),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            // Gradient overlay
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(MaterialTheme.sizes.imageOverlayHeight)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.55f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(MaterialTheme.spacing.md),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                title?.let {
                    DiscogsHeadlineText(
                        text = it,
                        color = Color.White,
                        maxLines = 1
                    )
                }

                subtitle?.let {
                    DiscogsBodyText(
                        text = it,
                        color = Color.White.copy(alpha = 0.9f),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DiscogsFullImageCardUrlPreview() {
    DiscogsChallengeTheme {
        DiscogsFullImageCard(
            title = "Network Image Card",
            subtitle = "This is a subtitle",
            imageUrl = "https://via.placeholder.com/400x200.png"
        )
    }
}

