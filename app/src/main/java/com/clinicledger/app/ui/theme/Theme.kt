package com.clinicledger.app.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = ClinicBlue700,
    onPrimary = ClinicSurface,
    primaryContainer = ClinicBlue100,
    onPrimaryContainer = ClinicBlue900,
    secondary = ClinicBlue600,
    onSecondary = ClinicSurface,
    secondaryContainer = ClinicViolet100,
    onSecondaryContainer = ClinicViolet900,
    tertiary = ClinicSuccess,
    background = ClinicBackground,
    onBackground = ClinicTextPrimary,
    surface = ClinicSurface,
    onSurface = ClinicTextPrimary,
    surfaceVariant = ClinicSurfaceVariant,
    onSurfaceVariant = ClinicTextSecondary,
    error = ClinicDanger,
    outline = ClinicOutline,
    outlineVariant = ClinicBorder
)

@Composable
fun ClinicLedgerTheme(content: @Composable () -> Unit) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
