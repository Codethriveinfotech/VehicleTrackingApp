package com.vehicletrackingapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme(
    primary = BrandBlue,
    secondary = BrandIndigo,
    tertiary = BrandCyan,
    background = BgLuxury,
    surface = SurfaceLuxury,
    onPrimary = SurfaceLuxury,
    onBackground = TextTitle,
    onSurface = TextTitle,
    error = DangerCrimson
)

@Composable
fun VehicleTrackingAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = AppTypography,
        content = content
    )
}
