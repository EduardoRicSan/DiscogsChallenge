package com.tech.discogschallenge.presentation.ui.getArtistInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tech.design_system.common.constants.DSConstants.MAX_LINE_AT_4
import com.tech.design_system.components.text.DiscogsLabelText
import com.tech.design_system.tokens.spacing
import com.tech.discogschallenge.R
import com.tech.domain.model.getArtistInfo.ArtistFull

@Composable
fun ArtistQuickInfoSection(
    artist: ArtistFull,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        artist.dataQuality?.let {
            DiscogsLabelText(text = "${stringResource(R.string.title_data_quality)} $it")
        }

        if (artist.nameVariations.isNotEmpty()) {
            Spacer(Modifier.height(MaterialTheme.spacing.sm))

            DiscogsLabelText(
                text = "${stringResource(R.string.title_also_known_as)} ${
                    artist.nameVariations.joinToString()
                }",
                maxLines = MAX_LINE_AT_4
            )
        }
    }
}

