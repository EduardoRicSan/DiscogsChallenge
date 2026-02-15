package com.tech.design_system.common.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface DiscogsUiText {
    data class StringRes(
        val resId: Int
    ) : DiscogsUiText

    data class Dynamic(
        val value: String
    ) : DiscogsUiText
}

@Composable
fun DiscogsUiText.asString(): String =
    when (this) {
        is DiscogsUiText.StringRes -> stringResource(id = resId)
        is DiscogsUiText.Dynamic -> value
    }
