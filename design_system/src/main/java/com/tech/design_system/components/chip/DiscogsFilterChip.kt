package com.tech.design_system.components.chip

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import com.tech.design_system.components.text.DiscogsLabelText
import com.tech.design_system.tokens.elevation
import com.tech.design_system.tokens.spacing

@Composable
fun DiscogsFilterChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {

    val containerColor by animateColorAsState(
        targetValue =
            if (selected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surfaceVariant,
        label = "chipContainerColor"
    )

    val contentColor by animateColorAsState(
        targetValue =
            if (selected)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onSurfaceVariant,
        label = "chipContentColor"
    )

    Surface(
        modifier = modifier
            .padding(end = MaterialTheme.spacing.sm)
            .clip(MaterialTheme.shapes.extraLarge)
            .clickable(
                enabled = enabled,
                role = Role.Button,
                onClick = onClick
            ),
        color = containerColor,
        tonalElevation = if (selected) MaterialTheme.elevation.level2 else
            MaterialTheme.elevation.level0,
        shape = MaterialTheme.shapes.extraLarge
    ) {

        DiscogsLabelText(
            text = text,
            color = contentColor,
            modifier = Modifier.padding(
                horizontal = MaterialTheme.spacing.md,
                vertical = MaterialTheme.spacing.sm
            )
        )
    }
}
