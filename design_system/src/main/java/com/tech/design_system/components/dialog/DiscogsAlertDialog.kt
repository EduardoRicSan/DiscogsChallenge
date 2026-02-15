package com.tech.design_system.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tech.design_system.common.model.DiscogsDialogMessage
import com.tech.design_system.theme.DiscogsChallengeTheme

/**
 * A reusable alert dialog component for displaying messages, confirmations, and optional dismissal.
 *
 * This composable wraps Material3 [AlertDialog] and provides a consistent interface
 * for title, message, confirm and optional dismiss actions following the design system.
 *
 * @param dialogMessage Object containing dialog title, message, labels, and callbacks.
 * @param isVisible Controls whether the dialog is currently visible.
 * @param onDismiss Callback invoked when the dialog is dismissed, either via system back press
 *                  or tapping outside the dialog.
 * @param modifier Modifier applied to the AlertDialog container.
 *
 * Example usage:
 * val showDialog = remember { mutableStateOf(true) }
 * DiscogsAlertDialog(
 *     dialogMessage = DiscogsDialogMessage(
 *         title = "Delete Item",
 *         message = "Are you sure you want to delete this item?",
 *         confirmLabel = "Delete",
 *         onConfirm = { /* handle confirm */ },
 *         dismissLabel = "Cancel",
 *         onDismiss = { /* handle dismiss */ }
 *     ),
 *     isVisible = showDialog.value,
 *     onDismiss = { showDialog.value = false }
 * )
 */
@Composable
fun DiscogsAlertDialog(
    dialogMessage: DiscogsDialogMessage,
    isVisible: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    if (!isVisible.value) return

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            isVisible.value = false
            dialogMessage.onDismiss?.invoke()
        },
        title = {
            Text(
                text = dialogMessage.title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                text = dialogMessage.message,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    isVisible.value = false
                    dialogMessage.onConfirm()
                }
            ) {
                Text(dialogMessage.confirmLabel)
            }
        },
        dismissButton = dialogMessage.dismissLabel?.let { label ->
            {
                TextButton(
                    onClick = {
                        isVisible.value = false
                        dialogMessage.onDismiss?.invoke()
                    }
                ) {
                    Text(label)
                }
            }
        }
    )
}
