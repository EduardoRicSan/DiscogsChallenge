package com.tech.design_system.common.model

data class DiscogsDialogMessage(
    val title: String,
    val message: String,
    val confirmLabel: String,
    val onConfirm: () -> Unit,
    val dismissLabel: String? = null,
    val onDismiss: (() -> Unit)? = null
)
