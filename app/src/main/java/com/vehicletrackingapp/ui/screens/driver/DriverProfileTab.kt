package com.vehicletrackingapp.ui.screens.driver

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vehicletrackingapp.R
import com.vehicletrackingapp.data.repo.AppRepository
import com.vehicletrackingapp.ui.screens.common.*
import kotlinx.coroutines.launch
import androidx.compose.ui.text.font.FontWeight
import com.vehicletrackingapp.ui.theme.*

@Composable
fun DriverProfileTab(driverId: String) {
    val drivers by AppRepository.getAllDrivers().collectAsState(initial = emptyList())
    val driver = drivers.firstOrNull { it.id == driverId }
    val scope = rememberCoroutineScope()

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var license by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<android.net.Uri?>(null) }
    var saved by remember { mutableStateOf(false) }

    LaunchedEffect(driver) {
        driver?.let {
            name = it.name
            phone = it.phone
            license = it.licenseNumber
            photoUri = it.photoUri?.let { uri -> android.net.Uri.parse(uri) }
        }
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        SectionTitle("DRIVER PROFILE")

        UltraGlassCard {
            CameraGalleryPicker(
                label = "Profile Identification",
                imageUri = photoUri,
                onImageSelected = { photoUri = it }
            )
            Spacer(modifier = Modifier.height(32.dp))
            FuturisticTextField(value = name, onValueChange = { name = it }, label = "Full Name", leadingIcon = Icons.Default.Person)
            Spacer(modifier = Modifier.height(20.dp))
            FuturisticTextField(value = phone, onValueChange = { phone = it }, label = "Phone Number", leadingIcon = Icons.Default.Phone, keyboardType = androidx.compose.ui.text.input.KeyboardType.Phone)
            Spacer(modifier = Modifier.height(20.dp))
            FuturisticTextField(value = license, onValueChange = { license = it }, label = "License Number", leadingIcon = Icons.Default.Badge)
            
            Spacer(modifier = Modifier.height(40.dp))
            GradientButton(text = "UPDATE PROFILE") {
                if (driver != null) {
                    scope.launch {
                        AppRepository.updateDriver(driver.copy(name = name, phone = phone, licenseNumber = license, photoUri = photoUri?.toString()))
                        saved = true
                    }
                }
            }
            if (saved) {
                Spacer(modifier = Modifier.height(16.dp))
                Text("✓ PROFILE UPDATED", color = SuccessEmerald, fontWeight = FontWeight.Black, modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
    }
}
