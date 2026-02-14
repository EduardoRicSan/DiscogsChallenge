package com.tech.design_system.common.preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.tech.design_system.theme.DiscogsChallengeTheme

@Composable
fun DiscogsPreview(
    content: @Composable () -> Unit
) {
    DiscogsChallengeTheme {
        Surface {
            content()
        }
    }
}
