package com.vehicletrackingapp.ui.screens.driver

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vehicletrackingapp.data.repo.AppRepository
import com.vehicletrackingapp.ui.screens.common.*
import com.vehicletrackingapp.ui.theme.*

@Composable
fun VehicleDetailsTab(driverId: String) {
    val allVehicles by AppRepository.getAllVehicles().collectAsState(initial = emptyList())
    val myVehicle = allVehicles.find { it.assignedDriverId == driverId }
    
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(modifier = Modifier.fillMaxSize()) {
        SectionTitle("FLEET INVENTORY")

        if (allVehicles.isEmpty()) {
            UltraGlassCard {
                Text("Waiting for Administrator to synchronize fleet...", color = TextHint)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 60.dp)
            ) {
                // Highlight My Vehicle first if assigned
                myVehicle?.let { vehicle ->
                    item {
                        StaggeredItem(visible, 0) {
                            UltraGlassCard {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier.size(64.dp).clip(RoundedCornerShape(16.dp)).background(BrandBlue.copy(alpha = 0.1f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (vehicle.imageUri != null) {
                                            AsyncImage(model = vehicle.imageUri, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                        } else {
                                            Icon(Icons.Default.DirectionsCar, null, tint = BrandBlue)
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("ASSIGNED TO YOU", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = SuccessEmerald)
                                        Text(vehicle.number, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black)
                                        Text(vehicle.model, style = MaterialTheme.typography.bodySmall, color = TextHint)
                                    }
                                }
                            }
                        }
                    }
                }

                items(allVehicles.filter { it.id != myVehicle?.id }) { vehicle ->
                    StaggeredItem(visible, 2) {
                        UltraGlassCard {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier.size(56.dp).clip(RoundedCornerShape(14.dp)).background(Color.Black.copy(alpha = 0.05f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (vehicle.imageUri != null) {
                                        AsyncImage(model = vehicle.imageUri, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                    } else {
                                        Icon(Icons.Default.DirectionsCar, null, tint = TextHint)
                                    }
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column {
                                    Text(vehicle.number, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                                    Text(vehicle.model, style = MaterialTheme.typography.bodySmall, color = TextHint)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
