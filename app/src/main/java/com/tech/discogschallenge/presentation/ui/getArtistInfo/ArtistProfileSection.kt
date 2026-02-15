package com.tech.discogschallenge.presentation.ui.getArtistInfo

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tech.design_system.components.text.DiscogsBodyText
import com.tech.design_system.components.text.DiscogsOverlineText
import com.tech.design_system.tokens.spacing
import com.tech.discogschallenge.R

@Composable
fun ArtistProfileSection(
    profile: String,
    modifier: Modifier = Modifier
) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
    ) {

        // ---------- SECTION TITLE ----------
        DiscogsOverlineText(
            text = stringResource(R.string.title_bio),
            style = MaterialTheme.typography.titleMedium
        )

        // ---------- EXPANDABLE TEXT ----------
        Box(
            modifier = Modifier.animateContentSize()
        ) {
            DiscogsBodyText(
                text = profile,
                maxLines = if (expanded) Int.MAX_VALUE else 5,
            )
        }

        // ---------- EXPAND / COLLAPSE ACTION ----------
        TextButton(
            onClick = { expanded = !expanded }
        ) {
            DiscogsBodyText(
                text = stringResource(if (expanded) R.string.action_show_less
                else R.string.action_read_more
                ),
            )
        }
    }
}
