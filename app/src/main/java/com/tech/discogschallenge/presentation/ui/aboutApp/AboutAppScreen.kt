package com.tech.discogschallenge.presentation.ui.aboutApp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.tech.design_system.common.model.DiscogsImageSource
import com.tech.design_system.components.image.DiscogsCircularImage
import com.tech.design_system.components.image.DiscogsImage
import com.tech.design_system.components.text.DiscogsBodyText
import com.tech.design_system.components.text.DiscogsHeadlineText
import com.tech.design_system.components.text.DiscogsSubtitleText
import com.tech.design_system.components.text.DiscogsTitleText
import com.tech.design_system.tokens.sizes
import com.tech.design_system.tokens.spacing
import com.tech.discogschallenge.R

@Composable
fun AboutAppScreen(
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Cover Image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.sizes.imageMedium)
        ) {
            DiscogsImage(
                source = DiscogsImageSource.Url(IMAGE_COVER),
                contentDescription = "Cover Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Profile Circular Image centrada en el bottom del cover
            DiscogsCircularImage(
                source = DiscogsImageSource.Url(IMAGE_PROFILE),
                contentDescription = "Profile Image",
                size = MaterialTheme.sizes.imageSmall,
                borderWidth = MaterialTheme.spacing.xs,
                borderColor = MaterialTheme.colorScheme.primary,
            )
        }

        Spacer(modifier = Modifier.height(MaterialTheme.sizes.imageSmall / 2 + MaterialTheme.spacing.lg))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Nombre y rol
            DiscogsHeadlineText(
                text = stringResource(R.string.title_dev_name)
            )
            DiscogsSubtitleText(
                text = stringResource(R.string.title_dev_bio),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            DiscogsTitleText(
                text = stringResource(R.string.title_dev_experience)
            )
            DiscogsBodyText(
                text = stringResource(R.string.title_dev_skills)
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            // Fit para Clara
            DiscogsTitleText(
                text = stringResource(R.string.title_dev_clara_fit)
            )
            DiscogsBodyText(
                text = stringResource(R.string.title_dev_experience_desc)
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            // Datos divertidos
            DiscogsTitleText(
                text = stringResource(R.string.title_more_info)
            )
            DiscogsBodyText(
                text = stringResource(R.string.title_hobbies)
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xl))
        }
    }
}

const val IMAGE_PROFILE =
    "https://media.licdn.com/dms/image/v2/D5635AQFzd3WxjYIcFA/profile-framedphoto-shrink_400_400/" +
            "B56ZxfoafhHUAc-/0/1771130955992?e=1771736400&v=" +
            "beta&t=YnehqAuGaqt5d6e08rMcDfA-vk4lZUFKF193ZGBchuU"

const val IMAGE_COVER =
    "https://wallpaperaccess.com/full/1338395.jpg"
