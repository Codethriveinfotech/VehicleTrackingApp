package com.vehicletrackingapp.ui.screens.driver

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vehicletrackingapp.data.repo.AppRepository
import com.vehicletrackingapp.ui.screens.common.SectionTitle
import com.vehicletrackingapp.ui.screens.common.UltraGlassCard
import com.vehicletrackingapp.ui.screens.common.StaggeredItem
import com.vehicletrackingapp.ui.theme.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Route

@Composable
fun DriverHistoryScreen(driverId: String) {
    val allSubmittedTrips by AppRepository.getSubmittedTrips().collectAsState(initial = emptyList())
    val driverTrips = allSubmittedTrips.filter { it.driverId == driverId }
    val vehicles by AppRepository.getAllVehicles().collectAsState(initial = emptyList())
    
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(modifier = Modifier.fillMaxSize()) {
        SectionTitle("PAST TRIP LOGS")
        
        if (driverTrips.isEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            UltraGlassCard {
                Text("No past missions found for your profile.", color = TextHint, style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 60.dp)
            ) {
                items(driverTrips, key = { it.id }) { trip ->
                    val vehicle = vehicles.find { it.id == trip.vehicleId }
                    StaggeredItem(visible, 2) {
                        UltraGlassCard {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(trip.startDate, style = MaterialTheme.typography.labelSmall, color = TextHint, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("${trip.sourceLocation} ➔ ${trip.destinationLocation}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black)
                                    Text(vehicle?.number ?: "Unassigned Vehicle", style = MaterialTheme.typography.bodySmall, color = BrandBlue, fontWeight = FontWeight.Bold)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text("${trip.startOdometer} KM", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = Color.Black)
                                    Text("START", style = MaterialTheme.typography.labelSmall, color = TextHint)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
