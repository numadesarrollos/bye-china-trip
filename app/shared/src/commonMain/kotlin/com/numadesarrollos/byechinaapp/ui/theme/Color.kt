package com.numadesarrollos.byechinaapp.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// ── Design tokens — from diseno/diseno-completo.html ──────────────────────────
val Cinnabar = Color(0xFFC9402E) // red thread  — primary
val Amber    = Color(0xFFB07A43) // 🐻 Borja    — secondary
val Blush    = Color(0xFFD5808F) // 🐰 Esther   — tertiary
val Gold     = Color(0xFFC2A05A) // special dates
val Paper    = Color(0xFFFCF7F2) // light background
val Ink      = Color(0xFF2B2521) // text on light bg

// ── Light scheme ──────────────────────────────────────────────────────────────
val LightColorScheme: ColorScheme = lightColorScheme(
    primary             = Color(0xFFC9402E),
    onPrimary           = Color(0xFFFFFFFF),
    primaryContainer    = Color(0xFFFFDAD5),
    onPrimaryContainer  = Color(0xFF410001),

    secondary           = Color(0xFFB07A43),
    onSecondary         = Color(0xFFFFFFFF),
    secondaryContainer  = Color(0xFFFFDCBE),
    onSecondaryContainer = Color(0xFF2D1600),

    tertiary            = Color(0xFFD5808F),
    onTertiary          = Color(0xFFFFFFFF),
    tertiaryContainer   = Color(0xFFFFD9DF),
    onTertiaryContainer = Color(0xFF3E0018),

    error               = Color(0xFFBA1A1A),
    onError             = Color(0xFFFFFFFF),
    errorContainer      = Color(0xFFFFDAD6),
    onErrorContainer    = Color(0xFF410002),

    background          = Color(0xFFFCF7F2), // Paper
    onBackground        = Color(0xFF2B2521), // Ink

    surface             = Color(0xFFFCF7F2),
    onSurface           = Color(0xFF2B2521),
    surfaceVariant      = Color(0xFFF5DDD8),
    onSurfaceVariant    = Color(0xFF534340),
    outline             = Color(0xFF857370),
    outlineVariant      = Color(0xFFD8C2BE),

    inverseSurface      = Color(0xFF382E2C),
    inverseOnSurface    = Color(0xFFFFEDE9),
    inversePrimary      = Color(0xFFFFB4A9),
    scrim               = Color(0xFF000000),
)

// ── Dark scheme ───────────────────────────────────────────────────────────────
val DarkColorScheme: ColorScheme = darkColorScheme(
    primary             = Color(0xFFFFB4A9),
    onPrimary           = Color(0xFF690003),
    primaryContainer    = Color(0xFF93000A),
    onPrimaryContainer  = Color(0xFFFFDAD5),

    secondary           = Color(0xFFFBB86D),
    onSecondary         = Color(0xFF4D2700),
    secondaryContainer  = Color(0xFF6D3C00),
    onSecondaryContainer = Color(0xFFFFDCBE),

    tertiary            = Color(0xFFFFB0BB),
    onTertiary          = Color(0xFF64002A),
    tertiaryContainer   = Color(0xFF8C1040),
    onTertiaryContainer = Color(0xFFFFD9DF),

    error               = Color(0xFFFFB4AB),
    onError             = Color(0xFF690005),
    errorContainer      = Color(0xFF93000A),
    onErrorContainer    = Color(0xFFFFDAD6),

    background          = Color(0xFF1A1110),
    onBackground        = Color(0xFFEEE0DC),

    surface             = Color(0xFF231918),
    onSurface           = Color(0xFFEEE0DC),
    surfaceVariant      = Color(0xFF534340),
    onSurfaceVariant    = Color(0xFFD8C2BE),
    outline             = Color(0xFFA08C89),
    outlineVariant      = Color(0xFF534340),

    inverseSurface      = Color(0xFFEEE0DC),
    inverseOnSurface    = Color(0xFF382E2C),
    inversePrimary      = Color(0xFFC9402E),
    scrim               = Color(0xFF000000),
)

// ── Custom semantic colors (not in Material3 slots) ───────────────────────────
// Access via AppTheme.customColors
data class CustomColors(
    val gold: Color,           // special dates
    val bear: Color,           // 🐻 Borja alias
    val bun: Color,            // 🐰 Esther alias
)

val LightCustomColors = CustomColors(
    gold = Gold,
    bear = Amber,
    bun  = Blush,
)

val DarkCustomColors = CustomColors(
    gold = Color(0xFFE8C07A),
    bear = Color(0xFFFBB86D),
    bun  = Color(0xFFFFB0BB),
)
