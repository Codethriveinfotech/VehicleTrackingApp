package com.vehicletrackingapp.ui.screens.admin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vehicletrackingapp.R
import com.vehicletrackingapp.ui.components.SpatialBackground
import com.vehicletrackingapp.ui.screens.common.BentoTile
import com.vehicletrackingapp.ui.screens.common.SectionTitle
import com.vehicletrackingapp.ui.theme.*
import com.vehicletrackingapp.data.repo.AppRepository
import kotlinx.coroutines.launch

private data class AdminMenuItem(
    val labelRes: Int,
    val icon: ImageVector,
    val tabIndex: Int
)

@Composable
fun AdminDashboardScreen(onLogout: () -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val menuItems = listOf(
        AdminMenuItem(R.string.reports, Icons.AutoMirrored.Filled.Assignment, 0),
        AdminMenuItem(R.string.admin_dashboard, Icons.Default.Dashboard, 1),
        AdminMenuItem(R.string.vehicle_management, Icons.Default.Settings, 2),
        AdminMenuItem(R.string.driver_management, Icons.Default.Group, 3),
        AdminMenuItem(R.string.vehicle_list, Icons.AutoMirrored.Filled.List, 4),
        AdminMenuItem(R.string.maintenance, Icons.Default.Build, 5),
        AdminMenuItem(R.string.profile, Icons.Default.Person, 6)
    )

    SpatialBackground {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier.width(320.dp),
                    drawerContainerColor = GlassWhite.copy(alpha = 0.95f),
                    drawerShape = RoundedCornerShape(topEnd = 32.dp, bottomEnd = 32.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Spacer(modifier = Modifier.height(56.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(BrandBlue.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = null,
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            Column {
                                Text(
                                    "ADMIN PORTAL",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Black,
                                    color = Color.Black
                                )
                                Text(
                                    "System Executive",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = TextHint,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        HorizontalDivider(color = Color.Black.copy(alpha = 0.05f))
                        Spacer(modifier = Modifier.height(16.dp))

                        menuItems.forEach { item ->
                            NavigationDrawerItem(
                                label = { Text(stringResource(item.labelRes), fontWeight = FontWeight.Bold) },
                                selected = selectedTab == item.tabIndex,
                                onClick = {
                                    selectedTab = item.tabIndex
                                    scope.launch { drawerState.close() }
                                },
                                icon = { Icon(item.icon, contentDescription = null) },
                                shape = RoundedCornerShape(16.dp),
                                colors = NavigationDrawerItemDefaults.colors(
                                    selectedContainerColor = BrandBlue.copy(alpha = 0.1f),
                                    selectedIconColor = BrandBlue,
                                    selectedTextColor = BrandBlue,
                                    unselectedContainerColor = Color.Transparent,
                                    unselectedIconColor = TextHint,
                                    unselectedTextColor = Color.Black
                                ),
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        
                        NavigationDrawerItem(
                            label = { Text(stringResource(R.string.logout), fontWeight = FontWeight.Black) },
                            selected = false,
                            onClick = onLogout,
                            icon = { Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null) },
                            colors = NavigationDrawerItemDefaults.colors(unselectedTextColor = DangerCrimson, unselectedIconColor = DangerCrimson),
                            modifier = Modifier.padding(bottom = 32.dp)
                        )
                    }
                }
            }
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = { 
                            Text(
                                stringResource(menuItems.find { it.tabIndex == selectedTab }?.labelRes ?: R.string.admin_dashboard), 
                                fontWeight = FontWeight.Black,
                                letterSpacing = 0.5.sp
                            ) 
                        },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Black)
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.White.copy(alpha = 0.7f),
                            titleContentColor = Color.Black
                        ),
                        modifier = Modifier.shadow(4.dp)
                    )
                }
            ) { padding ->
                Box(modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 20.dp)) {
                    when (selectedTab) {
                        0 -> ReportsTab()
                        1 -> AdminSummaryScreen()
                        2 -> VehicleManagementTab()
                        3 -> DriverManagementTab()
                        4 -> VehicleListTab()
                        5 -> MaintenanceHistoryScreen()
                        6 -> AdminProfileTab()
                    }
                }
            }
        }
    }
}

@Composable
fun AdminSummaryScreen() {
    val vehicles by AppRepository.getAllVehicles().collectAsState(initial = emptyList())
    val trips by AppRepository.getAllTrips().collectAsState(initial = emptyList())
    val drivers by AppRepository.getAllDrivers().collectAsState(initial = emptyList())
    val maintenance by AppRepository.getAllMaintenance().collectAsState(initial = emptyList())

    val activeVehicles = vehicles.size.toString()
    val onTrip = trips.count { it.endTime.isBlank() || it.status.equals("draft", ignoreCase = true) }.toString()
    val totalDrivers = drivers.size.toString()
    val maintenanceCount = maintenance.size.toString()

    Column(modifier = Modifier.fillMaxSize().padding(vertical = 16.dp)) {
        SectionTitle("FLEET OVERVIEW")
        
        Row(modifier = Modifier.fillMaxWidth()) {
            BentoTile(
                title = "ACTIVE VEHICLES",
                value = if (activeVehicles.length == 1) "0$activeVehicles" else activeVehicles,
                icon = Icons.Default.DirectionsCar,
                color = BrandBlue,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            BentoTile(
                title = "ON-TRIP",
                value = if (onTrip.length == 1) "0$onTrip" else onTrip,
                icon = Icons.Default.Route,
                color = SuccessEmerald,
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(modifier = Modifier.fillMaxWidth()) {
            BentoTile(
                title = "MAINTENANCE",
                value = if (maintenanceCount.length == 1) "0$maintenanceCount" else maintenanceCount,
                icon = Icons.Default.Build,
                color = WarningSunset,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            BentoTile(
                title = "TOTAL DRIVERS",
                value = if (totalDrivers.length == 1) "0$totalDrivers" else totalDrivers,
                icon = Icons.Default.Group,
                color = BrandIndigo,
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        Text("RECENT ACTIVITY", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black)
        Spacer(modifier = Modifier.height(12.dp))
        Text("No recent alerts in the last 24h.", color = TextHint, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun MaintenanceHistoryScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        SectionTitle("MAINTENANCE LOGS")
        Text("Detailed history will appear here.", color = TextHint)
    }
}
