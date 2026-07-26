package com.vehicletrackingapp.ui.screens.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.vehicletrackingapp.ui.theme.*
import java.io.File

/**
 * Standard Luxury Button with built-in bounce.
 */
@Composable
fun GradientButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.96f else 1f, label = "bounce")

    Button(
        onClick = onClick,
        enabled = enabled && !loading,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(12.dp, RoundedCornerShape(22.dp), ambientColor = BrandBlue.copy(alpha = 0.5f))
            .background(
                brush = Brush.horizontalGradient(PremiumGradient),
                shape = RoundedCornerShape(22.dp)
            ),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, disabledContainerColor = Color.Transparent),
        shape = RoundedCornerShape(22.dp),
        contentPadding = PaddingValues(),
        interactionSource = interactionSource
    ) {
        if (loading) {
            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp), strokeWidth = 3.dp)
        } else {
            Text(
                text = text, 
                fontWeight = FontWeight.Black, 
                fontSize = 17.sp, 
                letterSpacing = 1.2.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun UltraGlassCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 20.dp,
                shape = RoundedCornerShape(32.dp),
                clip = false,
                spotColor = Color.Black.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = GlassWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .border(1.dp, GlassBorder, RoundedCornerShape(32.dp))
                .padding(28.dp),
            content = content
        )
    }
}

@Composable
fun PremiumGlassCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) = UltraGlassCard(modifier, content)
@Composable
fun GlassCard(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) = UltraGlassCard(modifier, content)

typealias ColumnScope = androidx.compose.foundation.layout.ColumnScope

@Composable
fun FuturisticTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        targetValue = if (isFocused) BrandBlue else Color.Transparent,
        animationSpec = tween(400), label = "border"
    )
    val labelOffset by animateDpAsState(
        targetValue = if (isFocused || value.isNotEmpty()) (-28).dp else 0.dp,
        label = "labelOffset"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .background(Color.Black.copy(alpha = 0.04f), RoundedCornerShape(22.dp))
                .border(1.5.dp, borderColor, RoundedCornerShape(22.dp))
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = if (isFocused) BrandBlue else TextHint,
                        modifier = Modifier.size(26.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }

                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                    Text(
                        text = label,
                        color = if (isFocused) BrandBlue else TextHint,
                        fontSize = if (isFocused || value.isNotEmpty()) 12.sp else 16.sp,
                        fontWeight = if (isFocused || value.isNotEmpty()) FontWeight.Black else FontWeight.Bold,
                        modifier = Modifier.offset(y = labelOffset)
                    )

                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier.fillMaxWidth().padding(top = if (value.isNotEmpty() || isFocused) 18.dp else 0.dp),
                        textStyle = TextStyle(
                            color = TextTitle,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                        interactionSource = interactionSource,
                        cursorBrush = Brush.verticalGradient(listOf(BrandBlue, BrandBlue))
                    )
                }

                if (isPassword) {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = TextHint,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) = FuturisticTextField(value, onValueChange, label, modifier, isPassword, leadingIcon, keyboardType)

@Composable
fun CameraGalleryPicker(
    label: String,
    imageUri: Uri?,
    onImageSelected: (Uri) -> Unit
) {
    val context = LocalContext.current
    var pendingUri by remember { mutableStateOf<Uri?>(null) }
    
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && pendingUri != null) onImageSelected(pendingUri!!)
    }
    
    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) onImageSelected(uri)
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            launchCamera(context) { pendingUri = it; cameraLauncher.launch(it) }
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column {
        Text(label, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Black, color = TextTitle)
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .background(Color.Black.copy(alpha = 0.07f)),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri, 
                        contentDescription = null, 
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(Icons.Default.DirectionsCar, contentDescription = null, tint = TextHint.copy(alpha = 0.4f), modifier = Modifier.size(36.dp))
                }
            }
            Spacer(modifier = Modifier.width(24.dp))
            Column {
                OutlinedButton(
                    onClick = {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            launchCamera(context) { pendingUri = it; cameraLauncher.launch(it) }
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null, modifier = Modifier.size(18.dp))
                    Text(" CAMERA", fontWeight = FontWeight.Black, fontSize = 11.sp)
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = { galleryLauncher.launch("image/*") },
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Photo, contentDescription = null, modifier = Modifier.size(18.dp))
                    Text(" GALLERY", fontWeight = FontWeight.Black, fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
fun CameraOnlyPicker(
    label: String,
    imageUri: Uri?,
    onImageSelected: (Uri) -> Unit
) {
    val context = LocalContext.current
    var pendingUri by remember { mutableStateOf<Uri?>(null) }
    
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && pendingUri != null) onImageSelected(pendingUri!!)
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            launchCamera(context) { pendingUri = it; cameraLauncher.launch(it) }
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Column {
        Text(label, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Black, color = TextTitle)
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.Black.copy(alpha = 0.05f))
                    .clickable {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            launchCamera(context) { pendingUri = it; cameraLauncher.launch(it) }
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri, 
                        contentDescription = null, 
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null, tint = BrandBlue, modifier = Modifier.size(40.dp))
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("VERIFY", fontSize = 11.sp, color = BrandBlue, fontWeight = FontWeight.Black)
                    }
                }
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(
                    text = if (imageUri != null) "CAPTURED ✓" else "SECURE CAPTURE",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = if (imageUri != null) SuccessEmerald else BrandBlue
                )
                Text(
                    text = if (imageUri != null) "Tap to retake" else "Live photo only",
                    fontSize = 11.sp,
                    color = TextHint
                )
            }
        }
    }
}

private fun launchCamera(context: Context, onUriReady: (Uri) -> Unit) {
    try {
        val directory = File(context.filesDir, "images")
        if (!directory.exists()) directory.mkdirs()
        val file = File(directory, "img_${System.currentTimeMillis()}.jpg")
        val authority = "com.vehicletrackingapp.fileprovider"
        val uri = FileProvider.getUriForFile(context, authority, file)
        onUriReady(uri)
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Camera Error: ${e.message}", Toast.LENGTH_LONG).show()
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text, 
        style = MaterialTheme.typography.headlineSmall, 
        fontWeight = FontWeight.Black,
        modifier = Modifier.padding(vertical = 20.dp),
        color = TextTitle,
        letterSpacing = 1.2.sp
    )
}

@Composable
fun BentoTile(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(if (isPressed) 0.96f else 1f, label = "bounce")

    Card(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(16.dp, RoundedCornerShape(24.dp), ambientColor = color.copy(alpha = 0.3f))
            .clickable(interactionSource = interactionSource, indication = null) { },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = color, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Black, color = TextTitle)
            Text(title, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = TextHint, letterSpacing = 0.5.sp)
        }
    }
}

@Composable
fun StaggeredItem(visible: Boolean, index: Int, content: @Composable () -> Unit) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(600, delayMillis = index * 100)) + 
                slideInVertically(tween(600, delayMillis = index * 100), initialOffsetY = { it / 2 })
    ) {
        content()
    }
}
