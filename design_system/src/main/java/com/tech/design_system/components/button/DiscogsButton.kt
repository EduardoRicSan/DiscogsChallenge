package com.tech.design_system.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tech.design_system.common.preview.DiscogsPreview
import com.tech.design_system.tokens.radius
import com.tech.design_system.tokens.spacing

/**
 * Primary action button used for the main Call-to-Action (CTA) within a screen.
 *
 * This component represents the most prominent action and follows the
 * application's design system tokens for shape, spacing and typography
 * to ensure visual consistency across the app.
 *
 * @param text Text displayed inside the button.
 * @param onClick Callback invoked when the button is clicked.
 * @param modifier Modifier applied to the button.
 */
@Composable
fun DiscogsPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(MaterialTheme.radius.medium),
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.spacing.lg,
            vertical = MaterialTheme.spacing.sm
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}


/**
 * Secondary action button displayed using an outlined style.
 *
 * Intended for less prominent actions that should not visually compete
 * with the primary CTA. Styling is fully driven by design system tokens.
 *
 * @param text Text displayed inside the button.
 * @param onClick Callback invoked when the button is clicked.
 * @param modifier Modifier applied to the button.
 */
@Composable
fun DiscogsSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(MaterialTheme.radius.medium),
        contentPadding = PaddingValues(
            horizontal = MaterialTheme.spacing.lg,
            vertical = MaterialTheme.spacing.sm
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DiscogsPrimaryButtonPreview() {
    DiscogsPreview {
        DiscogsPrimaryButton(
            text = "Primary Action",
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DiscogsSecondaryButtonPreview() {
    DiscogsPreview {
        DiscogsSecondaryButton(
            text = "Secondary Action",
            onClick = {}
        )
    }
}
