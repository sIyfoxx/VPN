package com.example.vpn.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color.Companion.White
private val DarkColorScheme = darkColorScheme(
    primary = BambooGreen,
    secondary = BambooGold,
    tertiary = BambooLight,
    background = BackgroundDark,
    surface = SurfaceDark,
    error = BambooError,
    onPrimary = TextPrimaryDark,
    onSecondary = TextPrimaryDark,
    onBackground = TextPrimaryDark,
    onSurface = TextPrimaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = BambooGreen,
    secondary = BambooGold,
    tertiary = BambooLight,
    background = BambooCream,
    surface = White,
    error = BambooError,
    onPrimary = PandaBlack,
    onSecondary = PandaBlack,
    onBackground = PandaBlack,
    onSurface = PandaBlack
)

@Composable
fun PandaVPNTheme(
    darkTheme: Boolean = true,  // ← по умолчанию тёмная тема
    dynamicColor: Boolean = false,  // отключаем dynamic color для тёмной
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = PandaTypography,
        content = content
    )
}