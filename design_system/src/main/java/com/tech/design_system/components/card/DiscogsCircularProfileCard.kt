package com.tech.design_system.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tech.design_system.common.model.DiscogsImageSource
import com.tech.design_system.common.preview.DiscogsPreview
import com.tech.design_system.components.image.DiscogsCircularImage
import com.tech.design_system.tokens.spacing

/**
 * Profile card component displaying a circular image alongside a title.
 *
 * Supports both network and drawable resources as image sources and follows
 * the application's design system tokens for spacing, typography and layout.
 *
 * Built on top of [DiscogsCard] to ensure visual consistency across the app.
 *
 * @param modifier Modifier applied to the card container.
 * @param title Primary text displayed in the card.
 * @param imageUrl Optional image URL source.
 * @param imageRes Optional drawable resource image source.
 * @param onClick Optional click callback for card interaction.
 */
@Composable
fun DiscogsProfileCard(
    title: String,
    modifier: Modifier = Modifier,
    imageUrl: String? = null,
    imageRes: Int? = null,
    onClick: (() -> Unit)? = null
) {
    DiscogsCard(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {

            when {
                imageUrl != null -> {
                    DiscogsCircularImage(
                        source = DiscogsImageSource.Url(imageUrl)
                    )
                }

                imageRes != null -> {
                    DiscogsCircularImage(
                        source = DiscogsImageSource.Resource(imageRes)
                    )
                }
            }

            Spacer(modifier = Modifier.width(MaterialTheme.spacing.lg))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DiscogsProfileCardPreview() {
    DiscogsPreview {
        DiscogsProfileCard(
            title = "Daft Punk",
            imageUrl = null,
            imageRes = android.R.drawable.sym_def_app_icon,
            onClick = {}
        )
    }
}
