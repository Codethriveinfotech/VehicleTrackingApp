package com.vehicletrackingapp.ui.screens.driver

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vehicletrackingapp.R
import com.vehicletrackingapp.data.model.AppLanguage
import com.vehicletrackingapp.data.repo.AppRepository
import com.vehicletrackingapp.ui.components.SpatialBackground
import com.vehicletrackingapp.ui.theme.*
import com.vehicletrackingapp.util.LocaleHelper
import kotlinx.coroutines.launch

@Composable
fun DriverDashboardScreen(driverId: String, onLogout: () -> Unit) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val drivers by AppRepository.getAllDrivers().collectAsState(initial = emptyList())
    val driver = drivers.firstOrNull { it.id == driverId }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    
    var showLogoutDialog by remember { mutableStateOf(false) }
    var langMenuExpanded by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout Confirmation", fontWeight = FontWeight.Black) },
            text = { Text("Are you sure you want to exit the application?") },
            confirmButton = {
                TextButton(onClick = onLogout) {
                    Text("Logout", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", fontWeight = FontWeight.Bold)
                }
            },
            containerColor = SurfaceLuxury,
            shape = MaterialTheme.shapes.extraLarge
        )
    }

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
                        Spacer(modifier = Modifier.height(48.dp))
                        // Profile Header
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                                contentAlignment = Alignment.Center
                            ) {
                                if (driver?.photoUri != null) {
                                    AsyncImage(model = driver.photoUri, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                } else {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(32.dp))
                                }
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            Column {
                                Text(driver?.name ?: "Driver", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black)
                                Text("ID: ${driver?.id ?: ""}", style = MaterialTheme.typography.labelSmall, color = TextHint, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        HorizontalDivider(color = Color.Black.copy(alpha = 0.05f))
                        Spacer(modifier = Modifier.height(24.dp))

                        val items = listOf(
                            Triple("Trip Details", Icons.Default.Description, 0),
                            Triple("Trip History", Icons.Default.History, 2),
                            Triple("Settings", Icons.Default.Settings, 0),
                            Triple("Help & Support", Icons.AutoMirrored.Filled.Help, 0)
                        )

                        items.forEach { (label, icon, tab) ->
                            NavigationDrawerItem(
                                label = { Text(label, fontWeight = FontWeight.Bold) },
                                selected = false,
                                onClick = { 
                                    if (tab >= 0) selectedTab = tab
                                    scope.launch { drawerState.close() }
                                },
                                icon = { Icon(icon, contentDescription = null) },
                                shape = RoundedCornerShape(16.dp),
                                colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color.Transparent),
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        NavigationDrawerItem(
                            label = { Text("Logout", fontWeight = FontWeight.Black) },
                            selected = false,
                            onClick = { showLogoutDialog = true },
                            icon = { Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null) },
                            colors = NavigationDrawerItemDefaults.colors(unselectedTextColor = DangerCrimson, unselectedIconColor = DangerCrimson),
                            modifier = Modifier.padding(bottom = 32.dp)
                        )
                    }
                }
            },
            gesturesEnabled = true
        ) {
            Scaffold(
                containerColor = Color.Transparent,
                topBar = {
                    TopAppBar(
                        title = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo),
                                    contentDescription = null,
                                    modifier = Modifier.size(36.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    stringResource(R.string.app_name),
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Black,
                                    letterSpacing = 0.5.sp
                                )
                            }
                        },
                        actions = {
                            // Language Selector
                            Box {
                                TextButton(onClick = { langMenuExpanded = true }) {
                                    Icon(Icons.Default.Language, contentDescription = null, modifier = Modifier.size(20.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(LocaleHelper.currentLanguage.value.label, fontWeight = FontWeight.Black, fontSize = 12.sp)
                                }
                                DropdownMenu(expanded = langMenuExpanded, onDismissRequest = { langMenuExpanded = false }) {
                                    AppLanguage.entries.forEach { lang ->
                                        DropdownMenuItem(
                                            text = { Text(lang.label, fontWeight = FontWeight.Bold) },
                                            onClick = {
                                                LocaleHelper.setLocale(context, lang)
                                                langMenuExpanded = false
                                                (context as? android.app.Activity)?.recreate()
                                            }
                                        )
                                    }
                                }
                            }
                            // Profile Image
                            Box(
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                    .clickable { scope.launch { drawerState.open() } },
                                contentAlignment = Alignment.Center
                            ) {
                                if (driver?.photoUri != null) {
                                    AsyncImage(model = driver.photoUri, contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                                } else {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.White.copy(alpha = 0.7f),
                            titleContentColor = Color.Black
                        ),
                        modifier = Modifier.shadow(4.dp)
                    )
                },
                bottomBar = {
                    NavigationBar(
                        containerColor = Color.White.copy(alpha = 0.8f),
                        tonalElevation = 12.dp
                    ) {
                        val navItems = listOf(
                            Triple("Trip", Icons.Default.Route, 0),
                            Triple("Vehicles", Icons.Default.DirectionsCar, 1),
                            Triple("History", Icons.Default.History, 2),
                            Triple("Service", Icons.Default.Build, 3)
                        )
                        
                        navItems.forEach { (label, icon, tab) ->
                            NavigationBarItem(
                                selected = selectedTab == tab, 
                                onClick = { selectedTab = tab },
                                icon = { Icon(icon, contentDescription = null, modifier = Modifier.size(26.dp)) },
                                label = { Text(label, fontWeight = FontWeight.Black, fontSize = 11.sp) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = BrandBlue,
                                    selectedTextColor = BrandBlue,
                                    unselectedIconColor = TextHint,
                                    unselectedTextColor = TextHint,
                                    indicatorColor = BrandBlue.copy(alpha = 0.12f)
                                )
                            )
                        }
                    }
                }
            ) { padding ->
                Column(modifier = Modifier.fillMaxSize().padding(padding)) {
                    // Dashboard Header (Welcome Section)
                    if (selectedTab == 0) {
                        DriverDashboardHeader(driverName = driver?.name ?: "Driver", onLogoutClick = { showLogoutDialog = true })
                    }
                    
                    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
                        when (selectedTab) {
                            0 -> TripDetailsTab(driverId)
                            1 -> VehicleDetailsTab(driverId)
                            2 -> DriverHistoryScreen(driverId)
                            3 -> MaintenanceTab(driverId)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DriverDashboardHeader(driverName: String, onLogoutClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text("FLEET CAPTAIN", style = MaterialTheme.typography.labelMedium, color = TextHint, fontWeight = FontWeight.Black, letterSpacing = 1.sp)
            Text("Welcome back,", style = MaterialTheme.typography.bodyLarge)
            Text(driverName, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Black, color = Color.Black)
        }
        
        IconButton(
            onClick = onLogoutClick,
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.error.copy(alpha = 0.1f), CircleShape)
        ) {
            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout", tint = MaterialTheme.colorScheme.error)
        }
    }
}
