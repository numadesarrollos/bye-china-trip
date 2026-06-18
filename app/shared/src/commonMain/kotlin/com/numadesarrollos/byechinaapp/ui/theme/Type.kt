package com.numadesarrollos.byechinaapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// ── Custom font families ───────────────────────────────────────────────────────
// Font files (.ttf) must be placed in:
//   app/shared/src/commonMain/composeResources/font/
//
// Required files:
//   Fraunces_Regular.ttf     — https://fonts.google.com/specimen/Fraunces
//   Fraunces_SemiBold.ttf
//   Fraunces_Bold.ttf
//   Inter_Regular.ttf        — https://fonts.google.com/specimen/Inter
//   Inter_Medium.ttf
//   Inter_SemiBold.ttf
//   Caveat_Regular.ttf       — https://fonts.google.com/specimen/Caveat
//
// Once files are added, replace FontFamily.Default with:
//   val FrauncesFamily = FontFamily(
//       Font(Res.font.Fraunces_Regular),
//       Font(Res.font.Fraunces_SemiBold, FontWeight.SemiBold),
//       Font(Res.font.Fraunces_Bold, FontWeight.Bold),
//   )
//   val InterFamily = FontFamily(
//       Font(Res.font.Inter_Regular),
//       Font(Res.font.Inter_Medium, FontWeight.Medium),
//       Font(Res.font.Inter_SemiBold, FontWeight.SemiBold),
//   )

private val FrauncesFamily: FontFamily = FontFamily.Default  // TODO: replace with Fraunces
private val InterFamily: FontFamily    = FontFamily.Default  // TODO: replace with Inter
val CaveatFamily: FontFamily           = FontFamily.Default  // TODO: replace with Caveat

val AppTypography = Typography(
    // Display — Fraunces — big hero text, app title
    displayLarge  = TextStyle(fontFamily = FrauncesFamily, fontWeight = FontWeight.Bold,   fontSize = 57.sp, lineHeight = 64.sp),
    displayMedium = TextStyle(fontFamily = FrauncesFamily, fontWeight = FontWeight.Bold,   fontSize = 45.sp, lineHeight = 52.sp),
    displaySmall  = TextStyle(fontFamily = FrauncesFamily, fontWeight = FontWeight.Bold,   fontSize = 36.sp, lineHeight = 44.sp),

    // Headline — Fraunces — section titles, screen titles
    headlineLarge  = TextStyle(fontFamily = FrauncesFamily, fontWeight = FontWeight.SemiBold, fontSize = 32.sp, lineHeight = 40.sp),
    headlineMedium = TextStyle(fontFamily = FrauncesFamily, fontWeight = FontWeight.SemiBold, fontSize = 28.sp, lineHeight = 36.sp),
    headlineSmall  = TextStyle(fontFamily = FrauncesFamily, fontWeight = FontWeight.SemiBold, fontSize = 24.sp, lineHeight = 32.sp),

    // Title — Inter — card titles, list items, navigation
    titleLarge  = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.SemiBold, fontSize = 22.sp, lineHeight = 28.sp),
    titleMedium = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Medium,   fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.15.sp),
    titleSmall  = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Medium,   fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.1.sp),

    // Body — Inter — main content text
    bodyLarge  = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.5.sp),
    bodyMedium = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.25.sp),
    bodySmall  = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.4.sp),

    // Label — Inter — chips, tags, small UI labels
    labelLarge  = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.1.sp),
    labelMedium = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp),
    labelSmall  = TextStyle(fontFamily = InterFamily, fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp),
)
