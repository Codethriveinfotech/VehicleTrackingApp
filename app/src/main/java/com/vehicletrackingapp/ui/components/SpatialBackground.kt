package com.vehicletrackingapp.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.vehicletrackingapp.ui.theme.BgLuxury

/**
 * 2026 Spatial Foundation - Ultra stable static design.
 */
@Composable
fun SpatialBackground(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().background(BgLuxury)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            if (width == 0f || height == 0f) return@Canvas

            // Ultra-stable static soft glow
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF5856D6).copy(alpha = 0.05f), Color.Transparent),
                    center = Offset(width * 0.3f, height * 0.4f),
                    radius = width * 0.8f
                ),
                radius = width * 0.8f,
                center = Offset(width * 0.3f, height * 0.4f)
            )
        }
        content()
    }
}
