package com.numadesarrollos.byechinaapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

// ── Custom colors composition local ───────────────────────────────────────────

private val LocalCustomColors = staticCompositionLocalOf { LightCustomColors }

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme   = if (darkTheme) DarkColorScheme else LightColorScheme
    val customColors  = if (darkTheme) DarkCustomColors else LightCustomColors

    CompositionLocalProvider(LocalCustomColors provides customColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = AppTypography,
            content     = content,
        )
    }
}

// ── Extension to access custom colors from anywhere in the Compose tree ───────
// Usage: MaterialTheme.customColors.gold
val MaterialTheme.customColors: CustomColors
    @Composable @ReadOnlyComposable get() = LocalCustomColors.current
