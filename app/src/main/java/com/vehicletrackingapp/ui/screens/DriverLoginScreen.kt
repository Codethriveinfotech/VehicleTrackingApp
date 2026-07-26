package com.vehicletrackingapp.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vehicletrackingapp.R
import com.vehicletrackingapp.data.model.AppLanguage
import com.vehicletrackingapp.data.repo.AppRepository
import com.vehicletrackingapp.ui.components.SpatialBackground
import com.vehicletrackingapp.ui.screens.common.FuturisticTextField
import com.vehicletrackingapp.ui.screens.common.GradientButton
import com.vehicletrackingapp.ui.screens.common.UltraGlassCard
import com.vehicletrackingapp.ui.theme.*
import com.vehicletrackingapp.util.LocaleHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DriverLoginScreen(
    onLoginSuccess: (driverId: String) -> Unit,
    onGoToSignUp: () -> Unit,
    onGoToAdminLogin: () -> Unit,
    onLanguageChanged: () -> Unit
) {
    var identity by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    var langMenuExpanded by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) { visible = true }

    SpatialBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            
            AnimatedVisibility(visible = visible, enter = fadeIn() + slideInVertically()) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(130.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            Text(stringResource(R.string.app_name), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black, color = Color.Black, letterSpacing = 2.sp)
            Text("FLEET MANAGEMENT · 2026", color = TextHint, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)

            Spacer(modifier = Modifier.height(56.dp))

            AnimatedVisibility(visible = visible, enter = fadeIn(tween(1200))) {
                UltraGlassCard {
                    Text(
                        stringResource(R.string.driver_login), 
                        style = MaterialTheme.typography.titleLarge, 
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    FuturisticTextField(
                        value = identity, 
                        onValueChange = { identity = it }, 
                        label = stringResource(R.string.driver_id_placeholder),
                        leadingIcon = Icons.Default.Person
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    FuturisticTextField(
                        value = password, 
                        onValueChange = { password = it }, 
                        label = stringResource(R.string.password), 
                        isPassword = true,
                        leadingIcon = Icons.Default.Lock
                    )

                    error?.let {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(it, color = DangerCrimson, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                    GradientButton(
                        text = "SIGN IN TO FLEET",
                        loading = loading
                    ) {
                        if (identity.isNotBlank() && password.isNotBlank()) {
                            loading = true
                            scope.launch {
                                val driver = AppRepository.findDriver(identity, password)
                                delay(500) // Visual feedback for the high-end loading state
                                if (driver != null) {
                                    error = null
                                    onLoginSuccess(driver.id)
                                } else {
                                    error = context.getString(R.string.invalid_credentials)
                                    loading = false
                                }
                            }
                        } else {
                            error = "Please enter Driver ID and Password"
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }

        // Top Actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 56.dp, start = 20.dp, end = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                TextButton(
                    onClick = { langMenuExpanded = true },
                    modifier = Modifier.background(Color.White.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                ) {
                    Icon(Icons.Filled.Language, contentDescription = null, modifier = Modifier.size(18.dp), tint = BrandBlue)
                    Text("  " + LocaleHelper.currentLanguage.value.label, fontWeight = FontWeight.Black, color = Color.Black)
                }
                DropdownMenu(expanded = langMenuExpanded, onDismissRequest = { langMenuExpanded = false }) {
                    AppLanguage.entries.forEach { lang ->
                        DropdownMenuItem(
                            text = { Text(lang.label, fontWeight = FontWeight.Bold) },
                            onClick = {
                                LocaleHelper.setLocale(context, lang)
                                langMenuExpanded = false
                                onLanguageChanged()
                                (context as? android.app.Activity)?.recreate()
                            }
                        )
                    }
                }
            }
            IconButton(
                onClick = onGoToAdminLogin,
                modifier = Modifier.background(BrandBlue.copy(alpha = 0.1f), RoundedCornerShape(14.dp))
            ) {
                Icon(Icons.Filled.AdminPanelSettings, contentDescription = stringResource(R.string.admin_login), tint = BrandBlue)
            }
        }
    }
}
