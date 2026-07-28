package com.vehicletrackingapp.ui.screens.driver

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vehicletrackingapp.R
import com.vehicletrackingapp.data.model.MaintenanceRecord
import com.vehicletrackingapp.data.repo.AppRepository
import com.vehicletrackingapp.ui.screens.common.*
import com.vehicletrackingapp.util.PickerUtils
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.text.font.FontWeight
import com.vehicletrackingapp.ui.theme.*
import java.io.File
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MaintenanceTab(driverId: String) {
    val draftTrip by AppRepository.getDraftTrip(driverId).collectAsState(initial = null)
    val vehicles by AppRepository.getAllVehicles().collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    var selectedType by remember { mutableStateOf("") }
    var customType by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var billUri by remember { mutableStateOf<Uri?>(null) }
    var saved by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var typeMenuExpanded by remember { mutableStateOf(false) }

    val categories = listOf("Petrol", "Diesel", "Battery", "Wheel", "Service", "Other")

    val linkedVehicleId = draftTrip?.vehicleId
    val vehicle = linkedVehicleId?.let { id -> vehicles.find { it.id == id } }

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        SectionTitle("SERVICE REGISTRY")
        
        StaggeredItem(visible, 0) {
            if (vehicle == null) {
                UltraGlassCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, null, tint = DangerCrimson, modifier = Modifier.size(32.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Text("No vehicle assigned in Trip Details. Please select a vehicle first.", color = DangerCrimson, fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                UltraGlassCard {
                    Text("LOGGING SERVICE FOR", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = TextHint, letterSpacing = 1.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("${vehicle.number} — ${vehicle.model}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = BrandBlue)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        StaggeredItem(visible, 1) {
            UltraGlassCard {
                Text("EXPENSE DETAILS", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = TextHint, letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(16.dp))
                
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        value = if (selectedType == "Other") "Other: $customType" else selectedType.ifBlank { "Select Category" },
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Service Type", fontWeight = FontWeight.Bold) },
                        trailingIcon = { Icon(Icons.Default.ArrowDropDown, null, tint = BrandBlue) },
                        shape = RoundedCornerShape(20.dp),
                        enabled = false,
                        colors = OutlinedTextFieldDefaults.colors(disabledTextColor = Color.Black, disabledBorderColor = Color.Black.copy(alpha = 0.08f), disabledLabelColor = TextHint)
                    )
                    Box(modifier = Modifier.matchParentSize().background(Color.Transparent).clickable { typeMenuExpanded = true })
                    DropdownMenu(expanded = typeMenuExpanded, onDismissRequest = { typeMenuExpanded = false }) {
                        categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat, fontWeight = FontWeight.Bold) },
                                onClick = {
                                    selectedType = cat
                                    typeMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                if (selectedType == "Other") {
                    Spacer(modifier = Modifier.height(16.dp))
                    FuturisticTextField(value = customType, onValueChange = { customType = it }, label = "Specify Service Type", leadingIcon = Icons.Default.Edit)
                }

                Spacer(modifier = Modifier.height(16.dp))
                FuturisticTextField(value = description, onValueChange = { description = it }, label = "Work Description", leadingIcon = Icons.Default.Description)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        StaggeredItem(visible, 2) {
            UltraGlassCard {
                Text("VALUATION", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = TextHint, letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f).clickable { PickerUtils.showDatePicker(context) { date = it } }) {
                        OutlinedTextField(
                            value = date, onValueChange = {}, readOnly = true, enabled = false,
                            label = { Text("Date", color = TextHint) },
                            colors = OutlinedTextFieldDefaults.colors(disabledTextColor = Color.Black, disabledBorderColor = Color.Black.copy(alpha = 0.08f), disabledLabelColor = TextHint),
                            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                            leadingIcon = { Icon(Icons.Default.CalendarToday, null, tint = BrandBlue) }
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(modifier = Modifier.weight(1f).clickable { PickerUtils.showTimePicker(context) { time = it } }) {
                        OutlinedTextField(
                            value = time, onValueChange = {}, readOnly = true, enabled = false,
                            label = { Text("Time", color = TextHint) },
                            colors = OutlinedTextFieldDefaults.colors(disabledTextColor = Color.Black, disabledBorderColor = Color.Black.copy(alpha = 0.08f), disabledLabelColor = TextHint),
                            modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
                            leadingIcon = { Icon(Icons.Default.Schedule, null, tint = BrandBlue) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                FuturisticTextField(value = cost, onValueChange = { cost = it }, label = "Total Cost (INR)", keyboardType = androidx.compose.ui.text.input.KeyboardType.Number, leadingIcon = Icons.Default.CurrencyRupee)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        StaggeredItem(visible, 3) {
            UltraGlassCard {
                Text("DOCUMENTATION", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = TextHint, letterSpacing = 1.sp)
                Spacer(modifier = Modifier.height(16.dp))
                CameraOnlyPicker(label = "Service Bill Photo", imageUri = billUri, onImageSelected = { billUri = it })
            }
        }

        error?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(it, color = DangerCrimson, fontWeight = FontWeight.Black, modifier = Modifier.padding(horizontal = 12.dp))
        }

        Spacer(modifier = Modifier.height(32.dp))
        
        StaggeredItem(visible, 4) {
            GradientButton(text = "SUBMIT BILL") {
                if (vehicle == null) {
                    error = "ERROR: No active vehicle selected."
                } else if (selectedType.isBlank() || (selectedType == "Other" && customType.isBlank()) || description.isBlank() || date.isBlank() || cost.isBlank() || billUri == null) {
                    error = "ERROR: Complete all verification fields."
                } else {
                    scope.launch {
                        val finalType = if (selectedType == "Other") customType else selectedType
                        val success = AppRepository.upsertMaintenance(
                            MaintenanceRecord(
                                id = AppRepository.newId(),
                                vehicleId = vehicle.id,
                                driverId = driverId,
                                maintenanceType = finalType,
                                description = description,
                                date = date,
                                time = time,
                                cost = cost,
                                billImageUri = billUri?.toString(),
                                status = "submitted"
                            )
                        )
                        if (success) {
                            saved = true
                            error = null
                            // Clear
                            selectedType = ""; customType = ""; description = ""; date = ""; time = ""; cost = ""; billUri = null
                        } else {
                            error = "ERROR: Failed to save to database. Please check your connection."
                        }
                    }
                }
            }
        }
        
        if (saved) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("✓ RECORD SYNCHRONIZED", color = SuccessEmerald, fontWeight = FontWeight.Black, modifier = Modifier.align(Alignment.CenterHorizontally), letterSpacing = 1.sp)
        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}
