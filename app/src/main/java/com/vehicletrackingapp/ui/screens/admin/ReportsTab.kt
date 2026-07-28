package com.vehicletrackingapp.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Route
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vehicletrackingapp.R
import com.vehicletrackingapp.data.repo.AppRepository
import com.vehicletrackingapp.ui.screens.common.*
import com.vehicletrackingapp.ui.theme.*
import com.vehicletrackingapp.util.ExportUtils

@Composable
fun ReportsTab() {
    val submittedTrips by AppRepository.getAllTrips().collectAsState(initial = emptyList())
    val drivers by AppRepository.getAllDrivers().collectAsState(initial = emptyList())
    val vehicles by AppRepository.getAllVehicles().collectAsState(initial = emptyList())
    val context = LocalContext.current
    
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    var periodTab by remember { mutableIntStateOf(0) }
    val periods = listOf("Today", "Yesterday", "This Week", "This Month", "This Year")

    Column(modifier = Modifier.fillMaxSize().padding(vertical = 16.dp)) {
        SectionTitle("EXECUTIVE REPORTS")

        LazyRow {
            items(periods.size) { idx ->
                FilterChip(
                    selected = periodTab == idx,
                    onClick = { periodTab = idx },
                    label = { Text(periods[idx], fontWeight = FontWeight.Bold) },
                    modifier = Modifier.padding(end = 10.dp),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = BrandBlue.copy(alpha = 0.1f),
                        selectedLabelColor = BrandBlue
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))

        Text("SUBMITTED LOGS", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = TextHint, letterSpacing = 1.sp)
        
        if (submittedTrips.isEmpty()) {
            Spacer(modifier = Modifier.height(20.dp))
            UltraGlassCard {
                Text("No data synchronized for this period.", color = TextHint, style = MaterialTheme.typography.bodyMedium)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(submittedTrips, key = { it.id }) { trip ->
                    val driver = drivers.firstOrNull { it.id == trip.driverId }
                    val vehicle = vehicles.find { it.id == trip.vehicleId }
                    
                    StaggeredItem(visible, 2) {
                        UltraGlassCard {
                            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(driver?.name ?: "Unknown Driver", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black)
                                    Text(vehicle?.number ?: "UNASSIGNED", style = MaterialTheme.typography.labelSmall, color = BrandBlue, fontWeight = FontWeight.Bold)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Default.Route, null, tint = TextHint, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("${trip.sourceLocation} ➔ ${trip.destinationLocation}", style = MaterialTheme.typography.bodySmall, color = TextBody, fontWeight = FontWeight.SemiBold)
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("READING: ${trip.startOdometer} KM", color = TextHint, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                                }
                                
                                trip.startOdometerPhotoUri?.let { uri ->
                                    AsyncImage(
                                        model = uri,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(70.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(Color.Black.copy(alpha = 0.05f)),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        GradientButton(text = "EXPORT FLEET DATA (CSV)") {
            ExportUtils.exportTripsToCsv(context, submittedTrips)
        }
    }
}
