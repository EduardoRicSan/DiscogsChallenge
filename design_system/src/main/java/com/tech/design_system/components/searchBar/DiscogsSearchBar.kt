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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
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

    // 👇 Estado REAL del TextField (incluye cursor)
    var textFieldValue by rememberSaveable(
        stateSaver = TextFieldValue.Saver
    ) {
        mutableStateOf(TextFieldValue(query))
    }

    /**
     * Sync cuando el query viene del ViewModel
     * (ej: clear, restore state, navigation back)
     */
    LaunchedEffect(query) {
        if (query != textFieldValue.text) {
            textFieldValue = textFieldValue.copy(
                text = query,
                selection = TextRange(query.length)
            )
        }
    }

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = { newValue ->
            textFieldValue = newValue
            onQueryChange(newValue.text)
        },
        placeholder = { Text(placeholder) },
        singleLine = true,
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search")
        },
        trailingIcon = {
            if (textFieldValue.text.isNotEmpty()) {
                IconButton(onClick = {
                    textFieldValue = TextFieldValue("")
                    onQueryChange("")
                }) {
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
            onSearch = { onSearch?.invoke(textFieldValue.text) }
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
            errorIndicatorColor = errorColor
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
