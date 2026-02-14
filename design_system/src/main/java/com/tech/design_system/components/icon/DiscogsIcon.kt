package com.tech.design_system.components.icon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tech.design_system.theme.DiscogsChallengeTheme
import com.tech.design_system.tokens.sizes

/**
 * A reusable vector icon component with optional click support and theming.
 *
 * This composable wraps Material3 [Icon] and provides consistent size, tint,
 * and click behavior following the Discogs Design System.
 *
 * @param imageVector The [ImageVector] to display as the icon.
 * @param contentDescription Description for accessibility (TalkBack / screen readers).
 * @param modifier Modifier applied to the icon container.
 * @param tint Color applied to the icon. Defaults to [LocalContentColor].
 * @param onClick Optional callback invoked when the icon is clicked. If null, icon is not clickable.
 * @param size Icon size. Defaults to [MaterialTheme.sizes.icon].
 *
 * Example usage:
 * DiscogsIconVector(
 *     imageVector = Icons.Default.Star,
 *     contentDescription = "Favorite",
 *     onClick = { /* handle click */ }
 * )
 */
@Composable
fun DiscogsIconVector(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = LocalContentColor.current,
    onClick: (() -> Unit)? = null,
    size: Dp = MaterialTheme.sizes.iconSmall
) {
    val clickableModifier =
        if (onClick != null) modifier.clickable(onClick = onClick)
        else modifier

    Icon(
        imageVector = imageVector,
        contentDescription = contentDescription,
        modifier = clickableModifier.size(size),
        tint = tint
    )
}

@Preview(showBackground = true)
@Composable
private fun DiscogsIconVectorPreview() {
    DiscogsChallengeTheme {
        // State to simulate click
        var clicked by remember { mutableStateOf(false) }

        DiscogsIconVector(
            imageVector = Icons.Default.Star,
            contentDescription = "Star Icon",
            tint = if (clicked) Color.Yellow else Color.Gray,
            onClick = { clicked = !clicked } // toggle color on click
        )
    }
}
