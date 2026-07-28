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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vehicletrackingapp.R
import com.vehicletrackingapp.data.repo.AppRepository
import com.vehicletrackingapp.ui.components.SpatialBackground
import com.vehicletrackingapp.ui.screens.common.FuturisticTextField
import com.vehicletrackingapp.ui.screens.common.GradientButton
import com.vehicletrackingapp.ui.screens.common.UltraGlassCard
import com.vehicletrackingapp.ui.theme.BrandBlue
import com.vehicletrackingapp.ui.theme.DangerCrimson
import com.vehicletrackingapp.ui.theme.TextHint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AdminLoginScreen(onLoginSuccess: () -> Unit, onBack: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    
    LaunchedEffect(Unit) { visible = true }

    SpatialBackground {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                TopAppBar(
                    title = { Text("ADMIN ACCESS", fontWeight = FontWeight.Black, letterSpacing = 2.sp) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = BrandBlue)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White.copy(alpha = 0.5f))
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                
                AnimatedVisibility(visible = visible, enter = fadeIn() + slideInVertically()) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(48.dp))

                UltraGlassCard {
                    Text(
                        "SYSTEM EXECUTIVE", 
                        style = MaterialTheme.typography.titleMedium, 
                        fontWeight = FontWeight.Black,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    FuturisticTextField(
                        value = username, 
                        onValueChange = { username = it }, 
                        label = "Admin Username",
                        leadingIcon = Icons.Default.Person
                    )
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    FuturisticTextField(
                        value = password, 
                        onValueChange = { password = it }, 
                        label = "Secure Password", 
                        isPassword = true,
                        leadingIcon = Icons.Default.Lock
                    )

                    error?.let {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(it, color = DangerCrimson, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                    GradientButton(
                        text = "UNLOCK DASHBOARD",
                        loading = loading
                    ) {
                        if (username.isNotBlank() && password.isNotBlank()) {
                            loading = true
                            scope.launch {
                                delay(600) // Authenticating feel
                                val repository = AppRepository
                                if (repository.loginAdmin(username, password)) {
                                    error = null
                                    onLoginSuccess()
                                } else {
                                    error = "Access Denied: Invalid Credentials"
                                    loading = false
                                }
                            }
                        } else {
                            error = "Please enter Admin credentials"
                        }
                    }
                }
            }
        }
    }
}
