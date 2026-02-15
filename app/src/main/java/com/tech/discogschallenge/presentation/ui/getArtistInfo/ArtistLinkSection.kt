package com.tech.discogschallenge.presentation.ui.getArtistInfo

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.tech.design_system.components.button.DiscogsSecondaryButton
import com.tech.design_system.components.text.DiscogsOverlineText
import com.tech.design_system.tokens.spacing
import androidx.core.net.toUri
import com.tech.discogschallenge.R

@Composable
fun ArtistLinksSection(
    urls: List<String>,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Column(modifier = modifier) {

        DiscogsOverlineText(text = stringResource(R.string.title_external_links))

        Spacer(Modifier.height(MaterialTheme.spacing.sm))

        urls.forEach { url ->

            DiscogsSecondaryButton(
                text = url,
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(MaterialTheme.spacing.sm))
        }
    }
}

