package com.vehicletrackingapp.data.repo

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.vehicletrackingapp.data.model.Driver
import com.vehicletrackingapp.data.model.MaintenanceRecord
import com.vehicletrackingapp.data.model.TripEntry
import com.vehicletrackingapp.data.model.Vehicle
import com.vehicletrackingapp.data.remote.ApiService
import com.vehicletrackingapp.data.remote.LoginRequest
import com.vehicletrackingapp.data.remote.UserDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

/**
 * Enterprise Repository connected directly to Remote Database via ApiService.
 */
object AppRepository {

    private var sessionManager: com.vehicletrackingapp.data.local.SessionManager? = null
    lateinit var api: ApiService
        private set
        
    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    private val _vehicles = MutableStateFlow<List<Vehicle>>(emptyList())
    private val _drivers = MutableStateFlow<List<Driver>>(emptyList())
    private val _submittedTrips = MutableStateFlow<List<TripEntry>>(emptyList())
    private val _submittedMaintenance = MutableStateFlow<List<MaintenanceRecord>>(emptyList())
    
    private val _draftTrip = MutableStateFlow<TripEntry?>(null)
    private val _draftMaintenance = MutableStateFlow<MaintenanceRecord?>(null)

    fun init(context: Context) {
        if (sessionManager != null) return
        val sm = com.vehicletrackingapp.data.local.SessionManager(context)
        sessionManager = sm
        api = com.vehicletrackingapp.data.remote.RetrofitClient.create(sm)
        prefs = context.getSharedPreferences("app_drafts", Context.MODE_PRIVATE)
        
        loadDrafts()
        
        CoroutineScope(Dispatchers.IO).launch {
            syncPendingData()
        }
    }
    
    private fun loadDrafts() {
        val tripJson = prefs.getString("draft_trip", null)
        if (tripJson != null) _draftTrip.value = gson.fromJson(tripJson, TripEntry::class.java)
        
        val mainJson = prefs.getString("draft_maintenance", null)
        if (mainJson != null) _draftMaintenance.value = gson.fromJson(mainJson, MaintenanceRecord::class.java)
    }
    
    private fun saveDraftTrip(trip: TripEntry?) {
        _draftTrip.value = trip
        prefs.edit().putString("draft_trip", if (trip != null) gson.toJson(trip) else null).apply()
    }
    
    private fun saveDraftMaintenance(record: MaintenanceRecord?) {
        _draftMaintenance.value = record
        prefs.edit().putString("draft_maintenance", if (record != null) gson.toJson(record) else null).apply()
    }

    suspend fun findDriver(identity: String, password: String): Driver? = try {
        val response = api.login(LoginRequest(identity, password))
        if (response.isSuccessful && response.body()?.success == true) {
            val authData = response.body()?.data
            if (authData != null) {
                sessionManager?.saveAuthToken(authData.accessToken)
                Driver(
                    id = authData.user.id,
                    name = authData.user.name,
                    phone = authData.user.phone,
                    email = authData.user.email ?: "",
                    licenseNumber = authData.user.licenseNumber ?: "",
                    photoUri = authData.user.photoUri ?: "",
                    password = password
                )
            } else null
        } else null
    } catch (e: Exception) {
        Log.e("AppRepository", "findDriver error", e)
        null
    }

    suspend fun loginAdmin(username: String, password: String): Boolean = try {
        val response = api.login(LoginRequest(username, password))
        if (response.isSuccessful && response.body()?.success == true) {
            val authData = response.body()?.data
            if (authData != null) {
                sessionManager?.saveAuthToken(authData.accessToken)
                syncPendingData() // Fetch all data now that we are authenticated
                true
            } else false
        } else false
    } catch (e: Exception) {
        Log.e("AppRepository", "loginAdmin error", e)
        false
    }

    suspend fun signUp(driver: Driver) { 
        try { 
            val request = com.vehicletrackingapp.data.remote.RegisterRequest(
                id = driver.id,
                name = driver.name,
                email = driver.email,
                phone = driver.phone,
                password = driver.password,
                licenseNumber = driver.licenseNumber,
                photoUri = driver.photoUri
            )
            val response = api.signUp(request)
            if (!response.isSuccessful || response.body()?.success == false) {
                Log.e("AppRepository", "SignUp failed: ${response.errorBody()?.string() ?: response.body()?.message}")
            }
            fetchDrivers()
        } catch (e: Exception) {
            Log.e("AppRepository", "SignUp sync failed", e)
        } 
    }
    
    fun getAllVehicles(): Flow<List<Vehicle>> = _vehicles.asStateFlow()

    suspend fun addVehicle(vehicle: Vehicle) { 
        try { 
            api.saveVehicle(vehicle)
            fetchVehicles()
        } catch (e: Exception) {
            Log.e("AppRepository", "AddVehicle sync failed", e)
        } 
    }

    fun syncPendingData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                fetchVehicles()
                fetchDrivers()
                fetchTrips()
                fetchMaintenance()
                Log.d("AppRepository", "Data sync complete")
            } catch (e: Exception) {
                Log.e("AppRepository", "Periodic sync failed", e)
            }
        }
    }
    
    private suspend fun fetchVehicles() {
        val response = api.getVehicles()
        if (response.isSuccessful) response.body()?.data?.let { _vehicles.value = it }
    }
    
    private suspend fun fetchDrivers() {
        val response = api.getAllUsers()
        if (response.isSuccessful) {
            response.body()?.data?.let { users ->
                _drivers.value = users.map { 
                    Driver(
                        id = it.id,
                        name = it.name,
                        phone = it.phone,
                        licenseNumber = it.licenseNumber ?: "",
                        password = "1234",
                        photoUri = it.photoUri,
                        email = it.email ?: ""
                    )
                }
            }
        }
    }
    
    private suspend fun fetchTrips() {
        val response = api.getAllTrips()
        if (response.isSuccessful) response.body()?.data?.let { _submittedTrips.value = it }
    }
    
    private suspend fun fetchMaintenance() {
        val response = api.getAllMaintenance()
        if (response.isSuccessful) response.body()?.data?.let { _submittedMaintenance.value = it }
    }

    suspend fun updateVehicle(vehicle: Vehicle) { 
        try { 
            api.updateVehicle(vehicle.id, vehicle)
            fetchVehicles()
        } catch (e: Exception) {} 
    }
    
    suspend fun deleteVehicle(id: String) { 
        try { 
            api.deleteVehicle(id)
            fetchVehicles()
        } catch (e: Exception) {} 
    }

    fun getAllDrivers(): Flow<List<Driver>> = _drivers.asStateFlow()
    
    suspend fun updateDriver(driver: Driver) { 
        try { 
            api.updateUser(driver.id, UserDto(driver.id, driver.name, driver.email, driver.phone, driver.licenseNumber, driver.photoUri))
            fetchDrivers()
        } catch (e: Exception) {} 
    }
    
    suspend fun deleteDriver(id: String) { 
        try { 
            api.deleteUser(id)
            fetchDrivers()
        } catch (e: Exception) {} 
    }

    fun getDraftTrip(driverId: String): Flow<TripEntry?> = _draftTrip.asStateFlow()
    
    suspend fun upsertTrip(trip: TripEntry) { 
        try { 
            if (trip.status == "draft") {
                saveDraftTrip(trip)
            } else {
                saveDraftTrip(null) // clear draft
                val exists = _submittedTrips.value.any { it.id == trip.id }
                if (exists) {
                    api.updateTrip(trip.id, trip)
                } else {
                    api.createTrip(trip)
                }
                fetchTrips()
            }
        } catch (e: Exception) {
            Log.e("AppRepository", "upsertTrip sync failed", e)
        } 
    }
    fun getSubmittedTrips(): Flow<List<TripEntry>> = _submittedTrips.asStateFlow()

    fun getDraftMaintenance(driverId: String): Flow<MaintenanceRecord?> = _draftMaintenance.asStateFlow()
    
    suspend fun upsertMaintenance(record: MaintenanceRecord) { 
        try { 
            if (record.status == "draft") {
                saveDraftMaintenance(record)
            } else {
                saveDraftMaintenance(null)
                val exists = _submittedMaintenance.value.any { it.id == record.id }
                if (exists) {
                    api.updateMaintenance(record.id, record)
                } else {
                    api.createMaintenance(record)
                }
                fetchMaintenance()
            }
        } catch (e: Exception) {
            Log.e("AppRepository", "upsertMaintenance sync failed", e)
        } 
    }
    fun getSubmittedMaintenance(): Flow<List<MaintenanceRecord>> = _submittedMaintenance.asStateFlow()

    var adminUsername: String = "admin"
    var adminPassword: String = "admin123"

    fun newId(): String = UUID.randomUUID().toString().take(8)
}
