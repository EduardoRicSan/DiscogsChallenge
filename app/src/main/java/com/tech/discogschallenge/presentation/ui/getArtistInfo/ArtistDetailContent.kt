package com.tech.discogschallenge.presentation.ui.getArtistInfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.tech.design_system.common.constants.DSConstants.MAX_LINE_AT_2
import com.tech.discogschallenge.R
import com.tech.design_system.common.model.DiscogsImageSource
import com.tech.design_system.components.button.DiscogsPrimaryButton
import com.tech.design_system.components.button.DiscogsSecondaryButton
import com.tech.design_system.components.card.DiscogsCard
import com.tech.design_system.components.icon.DiscogsIconVector
import com.tech.design_system.components.image.DiscogsImage
import com.tech.design_system.components.text.DiscogsBodyText
import com.tech.design_system.components.text.DiscogsHeadlineText
import com.tech.design_system.components.text.DiscogsLabelText
import com.tech.design_system.components.text.DiscogsOverlineText
import com.tech.design_system.tokens.sizes
import com.tech.design_system.tokens.spacing
import com.tech.domain.model.getArtistInfo.ArtistFull

/**
 * Displays artist detail information including header image,
 * biography, navigation action, and band members list.
 */
@Composable
fun ArtistDetailContent(
    artist: ArtistFull,
    onViewAlbumsClick: () -> Unit,
    onViewAppInfoClick: () -> Unit
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        // --------------------------------------------------
        // HERO HEADER
        // --------------------------------------------------
        item {

            Box {

                val imageUrl =
                    artist.images.firstOrNull()?.resourceUrl ?: artist.thumb

                DiscogsImage(
                    source = DiscogsImageSource.Url(imageUrl.orEmpty()),
                    contentDescription = artist.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.sizes.imageLarge),
                    contentScale = ContentScale.Crop
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(MaterialTheme.sizes.imageLarge)
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Black.copy(alpha = 0.5f),
                                    Color.Transparent
                                )
                            )
                        )
                )

                DiscogsHeadlineText(
                    text = artist.name,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(MaterialTheme.spacing.lg)
                )
            }
        }

        // --------------------------------------------------
        // BIOGRAPHY
        // --------------------------------------------------
        artist.profile?.takeIf { it.isNotBlank() }?.let { profile ->

            item {
                ArtistProfileSection(
                    profile = profile,
                    modifier = Modifier.padding(MaterialTheme.spacing.lg)
                )
            }
        }

        // --------------------------------------------------
        // QUICK INFO
        // --------------------------------------------------
        item {
            ArtistQuickInfoSection(
                artist = artist,
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.lg)
            )
        }

        // --------------------------------------------------
        // MEMBERS
        // --------------------------------------------------
        if (artist.members.isNotEmpty()) {

            item {
                DiscogsOverlineText(
                    text = stringResource(R.string.title_band_members),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(
                        horizontal = MaterialTheme.spacing.lg,
                        vertical = MaterialTheme.spacing.sm
                    )
                )
            }

            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.lg),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
                ) {

                    items(artist.members) { member ->

                        DiscogsCard(
                            modifier = Modifier
                                .width(MaterialTheme.sizes.imageMedium)
                                .height(MaterialTheme.sizes.imageMedium)
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(MaterialTheme.spacing.sm)
                            ) {

                                DiscogsIconVector(
                                    Icons.Default.Person,
                                    contentDescription = null
                                )

                                Spacer(Modifier.width(MaterialTheme.spacing.sm))

                                DiscogsBodyText(
                                    text = member.name.orEmpty(),
                                    maxLines = MAX_LINE_AT_2
                                )
                            }
                        }
                    }
                }
            }
        }

        // --------------------------------------------------
        // EXTERNAL LINKS
        // --------------------------------------------------
        if (artist.urls.isNotEmpty()) {

            item {
                ArtistLinksSection(
                    urls = artist.urls,
                    modifier = Modifier.padding(MaterialTheme.spacing.lg)
                )
            }
        }

        // --------------------------------------------------
        // ACTIONS
        // --------------------------------------------------
        item {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.lg),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
            ) {

                DiscogsPrimaryButton(
                    text = stringResource(R.string.title_action_view_albums),
                    onClick = onViewAlbumsClick,
                    modifier = Modifier.fillMaxWidth()
                )

                DiscogsSecondaryButton(
                    text = stringResource(R.string.title_about_app),
                    onClick = onViewAppInfoClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
