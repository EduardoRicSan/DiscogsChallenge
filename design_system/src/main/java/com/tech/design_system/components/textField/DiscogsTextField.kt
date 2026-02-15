package com.tech.design_system.components.textField

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tech.design_system.tokens.spacing

/**
 * DiscogsSimpleTextField
 *
 * A basic TextField following the Discogs design system.
 * Supports placeholder text, single line input, and enabled/disabled state.
 *
 * @param value Current text value of the TextField.
 * @param onValueChange Callback invoked when the text changes.
 * @param placeholder Placeholder text displayed when empty.
 * @param modifier Modifier applied to the TextField.
 * @param singleLine Whether the TextField should be a single line.
 * @param enabled Whether the TextField is enabled for input.
 *
 * Example usage:
 * DiscogsSimpleTextField(
 *     value = query,
 *     onValueChange = { query = it },
 *     placeholder = "Enter name"
 * )
 */
@Composable
fun DiscogsSimpleTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    enabled: Boolean = true
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, style = MaterialTheme.typography.bodyMedium) },
        singleLine = singleLine,
        enabled = enabled,
        modifier = modifier.fillMaxWidth()
    )
}

/**
 * DiscogsOutlinedTextField
 *
 * Outlined variant of a TextField following the Discogs design system.
 * Supports placeholder text, single line input, and enabled/disabled state.
 *
 * @param value Current text value of the TextField.
 * @param onValueChange Callback invoked when the text changes.
 * @param placeholder Placeholder text displayed when empty.
 * @param modifier Modifier applied to the TextField.
 * @param singleLine Whether the TextField should be a single line.
 * @param enabled Whether the TextField is enabled for input.
 *
 * Example usage:
 * DiscogsOutlinedTextField(
 *     value = query,
 *     onValueChange = { query = it },
 *     placeholder = "Enter email"
 * )
 */
@Composable
fun DiscogsOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    enabled: Boolean = true
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, style = MaterialTheme.typography.bodyMedium) },
        singleLine = singleLine,
        enabled = enabled,
        modifier = modifier.fillMaxWidth()
    )
}

/**
 * Preview for Discogs text fields
 */
@Preview(showBackground = true)
@Composable
fun DiscogsTextFieldsPreview() {
    var simpleText by remember { mutableStateOf("") }
    var outlinedText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
    ) {
        DiscogsSimpleTextField(
            value = simpleText,
            onValueChange = { simpleText = it },
            placeholder = "Simple TextField"
        )

        DiscogsOutlinedTextField(
            value = outlinedText,
            onValueChange = { outlinedText = it },
            placeholder = "Outlined TextField"
        )
    }
}


