package com.tech.discogschallenge.presentation.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tech.design_system.common.model.DiscogsImageSource
import com.tech.design_system.components.card.DiscogsCard
import com.tech.design_system.components.image.DiscogsCircularImage
import com.tech.design_system.components.text.DiscogsTitleText
import com.tech.design_system.tokens.sizes
import com.tech.design_system.tokens.spacing
import com.tech.domain.model.search.SearchArtistResult

/**
 * Artist list item displaying basic artist information.
 */
@Composable
fun ArtistItem(
    artist: SearchArtistResult,
    onArtistItemClick: (Int) -> Unit,
) {
    DiscogsCard(
        onClick = { onArtistItemClick(artist.id) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {

            DiscogsCircularImage(
                source = DiscogsImageSource.Url(artist.thumb ?: ""),
                size = MaterialTheme.sizes.avatarLarge
            )

            Spacer(modifier = Modifier.width(MaterialTheme.spacing.md))

            Column(modifier = Modifier.fillMaxWidth()) {
                DiscogsTitleText(text = artist.title.orEmpty())
            }
        }
    }
}
