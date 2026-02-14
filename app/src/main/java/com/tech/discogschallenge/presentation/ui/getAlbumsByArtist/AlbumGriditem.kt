package com.tech.discogschallenge.presentation.ui.getAlbumsByArtist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.tech.design_system.R
import com.tech.design_system.common.constants.DSConstants.ALPHA_0_4
import com.tech.design_system.common.constants.DSConstants.ALPHA_0_7
import com.tech.design_system.common.constants.DSConstants.ALPHA_0_85
import com.tech.design_system.common.constants.DSConstants.MAX_LINE_AT_2
import com.tech.design_system.common.function.rememberAlbumColor
import com.tech.design_system.common.model.DiscogsImageSource
import com.tech.design_system.components.card.DiscogsCard
import com.tech.design_system.components.image.DiscogsImage
import com.tech.design_system.components.text.DiscogsBodyText
import com.tech.design_system.components.text.DiscogsLabelText
import com.tech.design_system.components.text.DiscogsOverlineText
import com.tech.design_system.tokens.spacing
import com.tech.domain.model.getAlbumsByArtist.Album

/**
 * Grid card representing a single album.
 * Displays artwork background, gradient overlay, and album metadata.
 */
@Composable
fun AlbumGridItem(
    album: Album
) {

    // Generates deterministic background color per album
    val baseColor = rememberAlbumColor(album.id)

    DiscogsCard(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        contentPadding = PaddingValues(MaterialTheme.spacing.none)
    ) {

        Box {

            // Background color layer
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(baseColor)
            )

            // Decorative background image
            DiscogsImage(
                source = DiscogsImageSource.Resource(R.drawable.ic_music_album),
                contentDescription = album.title,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(ALPHA_0_4),
                contentScale = ContentScale.Crop
            )

            // Gradient overlay for text readability
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = ALPHA_0_4),
                                Color.Black.copy(alpha = ALPHA_0_85)
                            )
                        )
                    )
            )

            // Album metadata content
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(MaterialTheme.spacing.md)
            ) {

                DiscogsBodyText(
                    text = album.title.orEmpty(),
                    color = Color.White,
                    maxLines = MAX_LINE_AT_2
                )

                album.year?.let {
                    DiscogsLabelText(
                        text = it.toString(),
                        color = Color.White.copy(alpha = ALPHA_0_85)
                    )
                }

                album.label?.let {
                    DiscogsOverlineText(
                        text = it,
                        color = Color.White.copy(alpha = ALPHA_0_7),
                    )
                }
            }
        }
    }
}
