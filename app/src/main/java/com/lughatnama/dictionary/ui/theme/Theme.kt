package com.lughatnama.dictionary.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = DeepAmber,
    onPrimary = WarmPaper,
    primaryContainer = ColorToken.LightGold,
    onPrimaryContainer = Charcoal,
    background = Ivory,
    onBackground = Charcoal,
    surface = WarmPaper,
    onSurface = Charcoal,
    surfaceVariant = ColorToken.LightVariant,
    onSurfaceVariant = MutedBrown,
    outline = SoftOutline,
)

private val DarkColors = darkColorScheme(
    primary = ColorToken.DarkGold,
    onPrimary = Night,
    primaryContainer = ColorToken.DarkGoldContainer,
    onPrimaryContainer = NightText,
    background = Night,
    onBackground = NightText,
    surface = NightSurface,
    onSurface = NightText,
    surfaceVariant = NightRaised,
    onSurfaceVariant = NightMuted,
    outline = NightOutline,
)

private object ColorToken {
    val LightGold = androidx.compose.ui.graphics.Color(0xFFF5DFB3)
    val LightVariant = androidx.compose.ui.graphics.Color(0xFFF1E7D8)
    val DarkGold = androidx.compose.ui.graphics.Color(0xFFE2B45E)
    val DarkGoldContainer = androidx.compose.ui.graphics.Color(0xFF594019)
}

@Composable
fun LughatNamaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = LughatTypography,
        content = content,
    )
}
