package com.vehicletrackingapp.data.repo

import android.content.Context
import android.util.Log
import com.vehicletrackingapp.data.local.AppDatabase
import com.vehicletrackingapp.data.model.Driver
import com.vehicletrackingapp.data.model.MaintenanceRecord
import com.vehicletrackingapp.data.model.TripEntry
import com.vehicletrackingapp.data.model.Vehicle
import com.vehicletrackingapp.data.remote.ApiService
import com.vehicletrackingapp.data.remote.LoginRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.UUID

/**
 * Enterprise Repository with Neon Postgres Sync logic.
 */
object AppRepository {

    @Volatile
    private var database: AppDatabase? = null
    
    private val dao get() = database?.dao()

    private var sessionManager: com.vehicletrackingapp.data.local.SessionManager? = null

    // We make api lateinit or lazy, initialized after context is provided.
    // However, as a singleton, we can just initialize it in `init()`.
    lateinit var api: ApiService
        private set

    fun init(context: Context) {
        if (database != null) return
        
        val sm = com.vehicletrackingapp.data.local.SessionManager(context)
        sessionManager = sm
        api = com.vehicletrackingapp.data.remote.RetrofitClient.create(sm)
        
        // Build database in IO thread to prevent main thread blocking/crashes
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val db = AppDatabase.getDatabase(context)
                database = db
                
                // Seed initial data if empty
                val drivers = db.dao().getAllDrivers().first()
                if (drivers.isEmpty()) {
                    db.dao().upsertDriver(Driver(id = "d1", name = "Sohith", phone = "9876543210", licenseNumber = "TN-01-2020-0001", password = "1234"))
                    db.dao().upsertDriver(Driver(id = "d2", name = "Dimpal", phone = "9876500000", licenseNumber = "TN-01-2019-0002", password = "1234"))
                }
                val vehicles = db.dao().getAllVehicles().first()
                if (vehicles.isEmpty()) {
                    db.dao().upsertVehicle(Vehicle(id = "v1", number = "TN-58-AB-1234", model = "Bobcat S450", assignedDriverId = "d1"))
                    db.dao().upsertVehicle(Vehicle(id = "v2", number = "TN-58-CD-5678", model = "JCB 135", assignedDriverId = "d2"))
                }
                Log.d("AppRepository", "Database initialized and seeded.")
            } catch (e: Exception) {
                Log.e("AppRepository", "Initialization failed", e)
            }
        }
    }

    suspend fun findDriver(identity: String, password: String): Driver? = try {
        // 1. Try Remote First
        val response = api.login(LoginRequest(identity, password))
        if (response.isSuccessful && response.body()?.success == true) {
            val authData = response.body()?.data
            if (authData != null) {
                sessionManager?.saveAuthToken(authData.accessToken)
                
                val driver = Driver(
                    id = authData.user.id,
                    name = authData.user.name,
                    phone = authData.user.phone,
                    email = authData.user.email ?: "",
                    licenseNumber = "", // Backend User doesn't have this yet, or we add it
                    password = password
                )
                dao?.upsertDriver(driver)
                driver
            } else null
        } else {
            // 2. Fallback to Local
            dao?.findDriver(identity, password)
        }
    } catch (e: Exception) {
        Log.e("AppRepository", "findDriver error, falling back to local", e)
        dao?.findDriver(identity, password)
    }

    suspend fun signUp(driver: Driver) { 
        try { 
            dao?.upsertDriver(driver) 
            val request = com.vehicletrackingapp.data.remote.RegisterRequest(
                id = driver.id,
                name = driver.name,
                email = driver.email,
                phone = driver.phone,
                password = driver.password
            )
            api.signUp(request) // Push to Neon
        } catch (e: Exception) {
            Log.e("AppRepository", "SignUp sync failed", e)
        } 
    }
    
    fun getAllVehicles(): Flow<List<Vehicle>> = dao?.getAllVehicles() ?: flowOf(emptyList())

    suspend fun addVehicle(vehicle: Vehicle) { 
        try { 
            dao?.upsertVehicle(vehicle) 
            api.saveVehicle(vehicle) // Push to Neon
        } catch (e: Exception) {
            Log.e("AppRepository", "AddVehicle sync failed", e)
        } 
    }

    fun syncPendingData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Fetch vehicles from remote
                val response = api.getVehicles()
                if (response.isSuccessful && response.body()?.success == true) {
                    response.body()?.data?.forEach { dao?.upsertVehicle(it) }
                }
                
                Log.d("AppRepository", "Data sync complete")
            } catch (e: Exception) {
                Log.e("AppRepository", "Periodic sync failed", e)
            }
        }
    }
    suspend fun updateVehicle(vehicle: Vehicle) { try { dao?.upsertVehicle(vehicle) } catch (e: Exception) {} }
    suspend fun deleteVehicle(id: String) { try { dao?.deleteVehicle(id) } catch (e: Exception) {} }

    fun getAllDrivers(): Flow<List<Driver>> = dao?.getAllDrivers() ?: flowOf(emptyList())
    suspend fun updateDriver(driver: Driver) { try { dao?.upsertDriver(driver) } catch (e: Exception) {} }
    suspend fun deleteDriver(id: String) { try { dao?.deleteDriver(id) } catch (e: Exception) {} }

    fun getDraftTrip(driverId: String): Flow<TripEntry?> = dao?.getDraftTrip(driverId) ?: flowOf(null)
    suspend fun upsertTrip(trip: TripEntry) { 
        try { 
            dao?.upsertTrip(trip)
            api.createTrip(trip)
        } catch (e: Exception) {
            Log.e("AppRepository", "upsertTrip sync failed", e)
        } 
    }
    fun getSubmittedTrips(): Flow<List<TripEntry>> = dao?.getSubmittedTrips() ?: flowOf(emptyList())

    fun getDraftMaintenance(driverId: String): Flow<MaintenanceRecord?> = dao?.getDraftMaintenance(driverId) ?: flowOf(null)
    suspend fun upsertMaintenance(record: MaintenanceRecord) { 
        try { 
            dao?.upsertMaintenance(record)
            api.createMaintenance(record)
        } catch (e: Exception) {
            Log.e("AppRepository", "upsertMaintenance sync failed", e)
        } 
    }
    fun getSubmittedMaintenance(): Flow<List<MaintenanceRecord>> = dao?.getSubmittedMaintenance() ?: flowOf(emptyList())

    var adminUsername: String = "admin"
    var adminPassword: String = "admin123"

    fun newId(): String = UUID.randomUUID().toString().take(8)
}
