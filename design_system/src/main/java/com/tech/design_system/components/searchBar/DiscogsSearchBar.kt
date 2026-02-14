package com.tech.design_system.components.searchBar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tech.design_system.theme.DiscogsChallengeTheme
import com.tech.design_system.tokens.spacing

/**
 * A customizable search bar with leading search icon, trailing clear icon, and optional search action.
 *
 * This composable uses [OutlinedTextField] internally and provides a standardized
 * height, shape, colors, and cursor styling according to the Discogs Design System.
 *
 * @param query Current text input value.
 * @param onQueryChange Callback invoked when the text changes.
 * @param onSearch Optional callback invoked when the search IME action is triggered.
 * @param placeholder Placeholder text displayed when the input is empty. Defaults to "Search...".
 * @param modifier Modifier applied to the search bar container.
 * @param backgroundColor Background color of the search bar. Defaults to [MaterialTheme.colorScheme.surface].
 * @param textColor Text color for input and icons. Defaults to [MaterialTheme.colorScheme.onBackground].
 * @param cursorColor Cursor color. Defaults to [MaterialTheme.colorScheme.primary].
 * @param errorColor Color used for error state. Defaults to [Color.Red].
 *
 * Example usage:
 * DiscogsSimpleSearchBar(
 *     query = searchQuery,
 *     onQueryChange = { searchQuery = it },
 *     onSearch = { performSearch(it) }
 * )
 */

@Composable
fun DiscogsSimpleSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: ((String) -> Unit)? = null,
    placeholder: String = "Search...",
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    cursorColor: Color = MaterialTheme.colorScheme.primary,
    errorColor: Color = Color.Red
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text(placeholder) },
        singleLine = true,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(Icons.Default.Clear, contentDescription = "Clear Search")
                }
            }
        },
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch?.invoke(query) }
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            disabledTextColor = textColor.copy(alpha = 0.3f),
            errorTextColor = errorColor,
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            disabledContainerColor = backgroundColor,
            errorContainerColor = backgroundColor,
            cursorColor = cursorColor,
            errorCursorColor = errorColor,
            focusedIndicatorColor = cursorColor,
            unfocusedIndicatorColor = textColor.copy(alpha = 0.3f),
            disabledIndicatorColor = textColor.copy(alpha = 0.3f),
            errorIndicatorColor = errorColor,
            focusedLeadingIconColor = cursorColor,
            unfocusedLeadingIconColor = textColor,
            disabledLeadingIconColor = textColor.copy(alpha = 0.3f),
            errorLeadingIconColor = errorColor,
            focusedTrailingIconColor = cursorColor,
            unfocusedTrailingIconColor = textColor,
            disabledTrailingIconColor = textColor.copy(alpha = 0.3f),
            errorTrailingIconColor = errorColor,
            focusedPlaceholderColor = textColor.copy(alpha = 0.5f),
            unfocusedPlaceholderColor = textColor.copy(alpha = 0.5f),
            disabledPlaceholderColor = textColor.copy(alpha = 0.3f),
            errorPlaceholderColor = errorColor,
            focusedLabelColor = textColor,
            unfocusedLabelColor = textColor.copy(alpha = 0.7f),
            disabledLabelColor = textColor.copy(alpha = 0.3f),
            errorLabelColor = errorColor,
            focusedSupportingTextColor = textColor,
            unfocusedSupportingTextColor = textColor.copy(alpha = 0.7f),
            disabledSupportingTextColor = textColor.copy(alpha = 0.3f),
            errorSupportingTextColor = errorColor,
            focusedPrefixColor = textColor,
            unfocusedPrefixColor = textColor.copy(alpha = 0.7f),
            disabledPrefixColor = textColor.copy(alpha = 0.3f),
            errorPrefixColor = errorColor,
            focusedSuffixColor = textColor,
            unfocusedSuffixColor = textColor.copy(alpha = 0.7f),
            disabledSuffixColor = textColor.copy(alpha = 0.3f),
            errorSuffixColor = errorColor
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun DiscogsSimpleSearchBarPreview() {
    DiscogsChallengeTheme {
        var query by remember { mutableStateOf("") }

        Column {
            DiscogsSimpleSearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { println("Searching for: $it") },
                placeholder = "Search for items..."
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            DiscogsSimpleSearchBar(
                query = query,
                onQueryChange = { query = it },
                onSearch = { println("Searching for: $it") },
                placeholder = "Search with error",
                errorColor = MaterialTheme.colorScheme.error
            )
        }
    }
}
