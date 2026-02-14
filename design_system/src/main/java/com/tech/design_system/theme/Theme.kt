package com.tech.design_system.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import com.tech.design_system.tokens.DiscogsElevation
import com.tech.design_system.tokens.DiscogsRadius
import com.tech.design_system.tokens.DiscogsSizes
import com.tech.design_system.tokens.DiscogsSpacing
import com.tech.design_system.tokens.LocalElevation
import com.tech.design_system.tokens.LocalRadius
import com.tech.design_system.tokens.LocalSize
import com.tech.design_system.tokens.LocalSpacing

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

@Composable
fun DiscogsChallengeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme)
                dynamicDarkColorScheme(context)
            else
                dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val spacing = DiscogsSpacing()
    val radius = DiscogsRadius()
    val elevation = DiscogsElevation()
    val sizes = DiscogsSizes()

    CompositionLocalProvider(
        LocalSpacing provides spacing,
        LocalRadius provides radius,
        LocalElevation provides elevation,
        LocalSize provides sizes,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

