package com.vehicletrackingapp.ui.screens.driver

import android.net.Uri
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vehicletrackingapp.R
import com.vehicletrackingapp.data.model.TripEntry
import com.vehicletrackingapp.data.repo.AppRepository
import com.vehicletrackingapp.ui.screens.common.*
import com.vehicletrackingapp.ui.theme.*
import com.vehicletrackingapp.util.PickerUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun TripDetailsTab(driverId: String) {
    val draftFlow = AppRepository.getDraftTrip(driverId).collectAsState(initial = null)
    val allVehicles by AppRepository.getAllVehicles().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100)
        visible = true
    }

    var tripId by remember { mutableStateOf("") }
    var selectedVehicleId by remember { mutableStateOf<String?>(null) }
    var source by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    
    var startDate by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var startOdo by remember { mutableStateOf("") }
    var startOdoUri by remember { mutableStateOf<Uri?>(null) }
    var startPlateUri by remember { mutableStateOf<Uri?>(null) }
    
    var endDate by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var endOdo by remember { mutableStateOf("") }
    var endOdoUri by remember { mutableStateOf<Uri?>(null) }
    
    var fuel by remember { mutableStateOf("Full") }
    var purpose by remember { mutableStateOf("Delivery") }
    var notes by remember { mutableStateOf("") }
    
    var error by remember { mutableStateOf<String?>(null) }
    var submitted by remember { mutableStateOf(false) }
    var vehicleMenuExpanded by remember { mutableStateOf(false) }
    var isInitialized by remember { mutableStateOf(false) }

    LaunchedEffect(draftFlow.value) {
        val draft = draftFlow.value
        if (draft != null && !isInitialized) {
            tripId = draft.id
            selectedVehicleId = draft.vehicleId
            source = draft.sourceLocation
            destination = draft.destinationLocation
            startDate = draft.startDate
            startTime = draft.startTime
            startOdo = draft.startOdometer
            startOdoUri = draft.startOdometerPhotoUri?.let { Uri.parse(it) }
            startPlateUri = draft.startVehiclePlatePhotoUri?.let { Uri.parse(it) }
            endDate = draft.endDate
            endTime = draft.endTime
            endOdo = draft.endOdometer
            endOdoUri = draft.endOdometerPhotoUri?.let { Uri.parse(it) }
            fuel = draft.fuelLevel
            purpose = draft.tripPurpose
            notes = draft.notes
            isInitialized = true
        } else if (draft == null && !isInitialized) {
            tripId = UUID.randomUUID().toString().take(8).uppercase()
            isInitialized = true
        }
    }

    fun persistDraft() {
        scope.launch {
            val trip = TripEntry(
                id = tripId,
                driverId = driverId,
                vehicleId = selectedVehicleId,
                sourceLocation = source,
                destinationLocation = destination,
                startDate = startDate,
                startTime = startTime,
                startOdometer = startOdo,
                startOdometerPhotoUri = startOdoUri?.toString(),
                startVehiclePlatePhotoUri = startPlateUri?.toString(),
                endDate = endDate,
                endTime = endTime,
                endOdometer = endOdo,
                endOdometerPhotoUri = endOdoUri?.toString(),
                fuelLevel = fuel,
                tripPurpose = purpose,
                notes = notes,
                status = "draft"
            )
            AppRepository.upsertTrip(trip)
        }
    }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        SectionTitle("TRIP REGISTRATION")
        
        StaggeredItem(visible, 0) {
            UltraGlassCard {
                Text("VEHICLE VERIFICATION", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = TextHint, letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(16.dp))
                CameraOnlyPicker(
                    label = "Vehicle License Plate Photo", 
                    imageUri = startPlateUri, 
                    onImageSelected = { startPlateUri = it; persistDraft() }
                )
                Spacer(modifier = Modifier.height(24.dp))
                Box(modifier = Modifier.fillMaxWidth()) {
                    val selectedVehicle = allVehicles.find { it.id == selectedVehicleId }
                    OutlinedTextField(
                        value = selectedVehicle?.let { "${it.number} (${it.model})" } ?: "Select Vehicle",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Active Fleet Asset", fontWeight = FontWeight.Bold) },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, tint = BrandBlue) },
                        shape = RoundedCornerShape(20.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = BrandBlue, unfocusedBorderColor = Color.Black.copy(alpha = 0.08f))
                    )
                    Box(modifier = Modifier.matchParentSize().background(Color.Transparent).clickable { vehicleMenuExpanded = true })
                    DropdownMenu(expanded = vehicleMenuExpanded, onDismissRequest = { vehicleMenuExpanded = false }) {
                        allVehicles.forEach { vehicle ->
                            DropdownMenuItem(
                                text = { Text("${vehicle.number} - ${vehicle.model}", fontWeight = FontWeight.Bold) },
                                onClick = {
                                    selectedVehicleId = vehicle.id
                                    vehicleMenuExpanded = false
                                    persistDraft()
                                }
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Start Trip Bento
        StaggeredItem(visible, 1) {
            UltraGlassCard {
                Text("START TRIP", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = TextHint, letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(16.dp))
                
                FuturisticTextField(value = source, onValueChange = { source = it; persistDraft() }, label = "Departure Point", leadingIcon = Icons.Default.LocationOn)
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f).clickable { PickerUtils.showDatePicker(context) { startDate = it; persistDraft() } }) {
                        OutlinedTextField(
                            value = startDate, onValueChange = {}, readOnly = true, enabled = false,
                            label = { Text("Start Date", color = TextHint) },
                            colors = OutlinedTextFieldDefaults.colors(disabledTextColor = Color.Black, disabledBorderColor = Color.Black.copy(alpha = 0.08f), disabledLabelColor = TextHint),
                            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                            leadingIcon = { Icon(Icons.Default.CalendarToday, null, tint = BrandBlue) }
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(modifier = Modifier.weight(1f).clickable { PickerUtils.showTimePicker(context) { startTime = it; persistDraft() } }) {
                        OutlinedTextField(
                            value = startTime, onValueChange = {}, readOnly = true, enabled = false,
                            label = { Text("Start Time", color = TextHint) },
                            colors = OutlinedTextFieldDefaults.colors(disabledTextColor = Color.Black, disabledBorderColor = Color.Black.copy(alpha = 0.08f), disabledLabelColor = TextHint),
                            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                            leadingIcon = { Icon(Icons.Default.Schedule, null, tint = BrandBlue) }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                FuturisticTextField(value = startOdo, onValueChange = { startOdo = it; persistDraft() }, label = "Odometer Reading", leadingIcon = Icons.Default.Speed, keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                Spacer(modifier = Modifier.height(20.dp))
                CameraOnlyPicker(label = "Odometer Reading Upload", imageUri = startOdoUri, onImageSelected = { startOdoUri = it; persistDraft() })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // End Trip Bento
        StaggeredItem(visible, 2) {
            UltraGlassCard {
                Text("END TRIP", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = TextHint, letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(16.dp))

                FuturisticTextField(value = destination, onValueChange = { destination = it; persistDraft() }, label = "Arrival Point", leadingIcon = Icons.Default.PinDrop)
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f).clickable { PickerUtils.showDatePicker(context) { endDate = it; persistDraft() } }) {
                        OutlinedTextField(
                            value = endDate, onValueChange = {}, readOnly = true, enabled = false,
                            label = { Text("End Date", color = TextHint) },
                            colors = OutlinedTextFieldDefaults.colors(disabledTextColor = Color.Black, disabledBorderColor = Color.Black.copy(alpha = 0.08f), disabledLabelColor = TextHint),
                            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                            leadingIcon = { Icon(Icons.Default.CalendarToday, null, tint = BrandBlue) }
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(modifier = Modifier.weight(1f).clickable { PickerUtils.showTimePicker(context) { endTime = it; persistDraft() } }) {
                        OutlinedTextField(
                            value = endTime, onValueChange = {}, readOnly = true, enabled = false,
                            label = { Text("End Time", color = TextHint) },
                            colors = OutlinedTextFieldDefaults.colors(disabledTextColor = Color.Black, disabledBorderColor = Color.Black.copy(alpha = 0.08f), disabledLabelColor = TextHint),
                            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                            leadingIcon = { Icon(Icons.Default.Schedule, null, tint = BrandBlue) }
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                FuturisticTextField(value = endOdo, onValueChange = { endOdo = it; persistDraft() }, label = "End Odometer Reading", leadingIcon = Icons.Default.Speed, keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                Spacer(modifier = Modifier.height(20.dp))
                CameraOnlyPicker(label = "End Odometer Photo Upload", imageUri = endOdoUri, onImageSelected = { endOdoUri = it; persistDraft() })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        StaggeredItem(visible, 3) {
            UltraGlassCard {
                Text("LOGISTICS", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = TextHint, letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(16.dp))
                FuturisticTextField(value = fuel, onValueChange = { fuel = it; persistDraft() }, label = "Fuel Level", leadingIcon = Icons.Default.LocalGasStation)
                Spacer(modifier = Modifier.height(16.dp))
                FuturisticTextField(value = purpose, onValueChange = { purpose = it; persistDraft() }, label = "Purpose of Trip", leadingIcon = Icons.Default.Work)
                Spacer(modifier = Modifier.height(16.dp))
                FuturisticTextField(value = notes, onValueChange = { notes = it; persistDraft() }, label = "Mission Notes", leadingIcon = Icons.AutoMirrored.Filled.Notes)
            }
        }

        error?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it, color = DangerCrimson, fontWeight = FontWeight.Black, modifier = Modifier.padding(horizontal = 8.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))
        
        StaggeredItem(visible, 4) {
            GradientButton(text = "VALIDATE & SUBMIT") {
                if (selectedVehicleId == null || source.isBlank() || destination.isBlank() || startOdo.isBlank() || startOdoUri == null) {
                    error = "ERROR: Complete verification data required."
                } else {
                    scope.launch {
                        val trip = draftFlow.value ?: return@launch
                        val success = AppRepository.upsertTrip(trip.copy(
                            vehicleId = selectedVehicleId,
                            sourceLocation = source, destinationLocation = destination,
                            startDate = startDate, startTime = startTime, startOdometer = startOdo,
                            startOdometerPhotoUri = startOdoUri?.toString(),
                            startVehiclePlatePhotoUri = startPlateUri?.toString(),
                            endDate = endDate, endTime = endTime, endOdometer = endOdo,
                            endOdometerPhotoUri = endOdoUri?.toString(),
                            fuelLevel = fuel, tripPurpose = purpose, notes = notes,
                            status = "submitted"
                        ))
                        if (success) {
                            submitted = true
                            error = null
                            // Clear form
                            selectedVehicleId = null; source = ""; destination = ""; startDate = ""; startTime = ""
                            startOdo = ""; startOdoUri = null; startPlateUri = null; endDate = ""; endTime = ""
                            endOdo = ""; endOdoUri = null; fuel = "Full"; purpose = "Delivery"; notes = ""
                            isInitialized = false 
                        } else {
                            error = "ERROR: Failed to save to database. Please check your connection."
                        }
                    }
                }
            }
        }
        
        if (submitted) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("✓ DATA SYNCHRONIZED", color = SuccessEmerald, fontWeight = FontWeight.Black, modifier = Modifier.align(Alignment.CenterHorizontally), letterSpacing = 1.sp)
        }
        
        Spacer(modifier = Modifier.height(60.dp))
    }
}
