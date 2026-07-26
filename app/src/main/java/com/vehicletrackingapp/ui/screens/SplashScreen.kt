package com.vehicletrackingapp.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vehicletrackingapp.R
import com.vehicletrackingapp.ui.theme.BgLuxury
import com.vehicletrackingapp.ui.theme.TextHint
import com.vehicletrackingapp.ui.theme.BrandBlue
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    val scale = remember { Animatable(0.8f) }
    val alpha = remember { Animatable(0f) }
    val shineProgress = rememberInfiniteTransition(label = "shine").animateFloat(
        initialValue = -1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shine"
    )

    LaunchedEffect(Unit) {
        scale.animateTo(1f, animationSpec = tween(800, easing = EaseOutBack))
        alpha.animateTo(1f, animationSpec = tween(600))
        delay(2200) // Ensure enough time for DB initialization
        onFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgLuxury),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(Color(0xFF5856D6).copy(alpha = 0.04f), radius = 100f, center = Offset(size.width * 0.2f, size.height * 0.3f))
            drawCircle(Color(0xFF32ADE6).copy(alpha = 0.04f), radius = 150f, center = Offset(size.width * 0.8f, size.height * 0.7f))
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.radialGradient(listOf(Color(0xFF007AFF).copy(alpha = 0.05f), BgLuxury)))
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "App Logo",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(160.dp)
                        .graphicsLayer {
                            scaleX = scale.value
                            scaleY = scale.value
                            this.alpha = alpha.value
                        }
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "VEHICLE TRACKING",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Black,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 4.sp,
                modifier = Modifier.graphicsLayer { this.alpha = alpha.value }
            )
            Text(
                text = "2026 ENTERPRISE EDITION",
                style = MaterialTheme.typography.labelMedium,
                color = TextHint,
                letterSpacing = 2.sp,
                modifier = Modifier.graphicsLayer { this.alpha = alpha.value }
            )
            
            Spacer(modifier = Modifier.height(60.dp))
            CircularProgressIndicator(
                color = BrandBlue,
                strokeWidth = 2.dp,
                modifier = Modifier.size(24.dp).graphicsLayer { this.alpha = alpha.value }
            )
        }
    }
}
