package com.tech.design_system.components.snackbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tech.design_system.common.model.DiscogsSnackbarMessage
import com.tech.design_system.common.model.DiscogsSnackbarType
import com.tech.design_system.theme.DiscogsChallengeTheme
import com.tech.design_system.tokens.spacing
import kotlinx.coroutines.delay
import java.util.UUID

/**
 * Top-aligned, animated Snackbar that supports different types (Success, Error, Warning, Info).
 *
 * The snackbar slides in from the top, shows an icon, message, and optional action button.
 * It automatically dismisses after the duration specified in [DiscogsSnackbarMessage].
 *
 * @param snackbarMessage Message data including type, text, duration, and optional action.
 * @param isVisible MutableState controlling the visibility of the snackbar.
 * @param onDismiss Optional callback invoked when the snackbar is dismissed.
 * @param modifier Modifier applied to the snackbar container.
 *
 * Example usage:
 * val isSnackbarVisible = remember { mutableStateOf(false) }
 * BXMasTopSnackbar(
 *     snackbarMessage = DiscogsSnackbarMessage(
 *         type = DiscogsSnackbarType.Success,
 *         message = "Item saved successfully!",
 *         durationMillis = 3000
 *     ),
 *     isVisible = isSnackbarVisible
 * )
 */

@Composable
fun BXMasTopSnackbar(
    snackbarMessage: DiscogsSnackbarMessage,
    isVisible: MutableState<Boolean>,
    onDismiss: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    val bgColor: Color
    val contentColor: Color
    val icon: ImageVector

    when (snackbarMessage.type) {
        DiscogsSnackbarType.Success -> {
            bgColor = Color(0xFF4CAF50)
            contentColor = Color.White
            icon = Icons.Default.CheckCircle
        }

        DiscogsSnackbarType.Error -> {
            bgColor = Color(0xFFF44336)
            contentColor = Color.White
            icon = Icons.Default.Error
        }

        DiscogsSnackbarType.Warning -> {
            bgColor = Color(0xFFFFC107)
            contentColor = Color.Black
            icon = Icons.Default.Warning
        }

        DiscogsSnackbarType.Info -> {
            bgColor = Color(0xFF2196F3)
            contentColor = Color.White
            icon = Icons.Default.Info
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 0.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedVisibility(
            visible = isVisible.value,
            enter = slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(400)),
            exit = slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(400))
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(bgColor)
                    .clickable {
                        isVisible.value = false
                        onDismiss?.invoke()
                    }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(icon, contentDescription = snackbarMessage.type.name, tint = contentColor)
                    Text(
                        text = snackbarMessage.message,
                        color = contentColor,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                snackbarMessage.actionLabel?.let { label ->
                    TextButton(onClick = {
                        snackbarMessage.onAction?.invoke()
                        isVisible.value = false
                        onDismiss?.invoke()
                    }) {
                        Text(label, color = contentColor)
                    }
                }
            }
        }
    }

    val currentKey = remember { mutableStateOf(UUID.randomUUID().toString()) }

    LaunchedEffect(snackbarMessage, currentKey.value) {
        isVisible.value = true
        delay(snackbarMessage.durationMillis)
        if (isVisible.value) {
            isVisible.value = false
            onDismiss?.invoke()
        }
        currentKey.value = UUID.randomUUID().toString()
    }
}

@Preview(showBackground = true)
@Composable
private fun BXMasTopSnackbarPreview() {
    DiscogsChallengeTheme {
        val isVisible = remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.md),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            BXMasTopSnackbar(
                snackbarMessage = DiscogsSnackbarMessage(
                    type = DiscogsSnackbarType.Success,
                    message = "Operation completed successfully",
                    durationMillis = 3000
                ),
                isVisible = isVisible
            )

            BXMasTopSnackbar(
                snackbarMessage = DiscogsSnackbarMessage(
                    type = DiscogsSnackbarType.Error,
                    message = "Something went wrong",
                    durationMillis = 3000,
                    actionLabel = "Retry",
                    onAction = { println("Retry clicked") }
                ),
                isVisible = remember { mutableStateOf(true) }
            )

            BXMasTopSnackbar(
                snackbarMessage = DiscogsSnackbarMessage(
                    type = DiscogsSnackbarType.Warning,
                    message = "Check your input",
                    durationMillis = 3000
                ),
                isVisible = remember { mutableStateOf(true) }
            )

            BXMasTopSnackbar(
                snackbarMessage = DiscogsSnackbarMessage(
                    type = DiscogsSnackbarType.Info,
                    message = "New update available",
                    durationMillis = 3000
                ),
                isVisible = remember { mutableStateOf(true) }
            )
        }
    }
}
