package com.vehicletrackingapp.data.local

import androidx.room.*
import com.vehicletrackingapp.data.model.Driver
import com.vehicletrackingapp.data.model.MaintenanceRecord
import com.vehicletrackingapp.data.model.TripEntry
import com.vehicletrackingapp.data.model.Vehicle
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    // Trips
    @Query("SELECT * FROM trips WHERE driverId = :driverId AND status = 'draft' LIMIT 1")
    fun getDraftTrip(driverId: String): Flow<TripEntry?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTrip(trip: TripEntry)

    @Query("SELECT * FROM trips WHERE status = 'submitted' ORDER BY startDate DESC, startTime DESC")
    fun getSubmittedTrips(): Flow<List<TripEntry>>

    // Maintenance
    @Query("SELECT * FROM maintenance WHERE driverId = :driverId AND status = 'draft' LIMIT 1")
    fun getDraftMaintenance(driverId: String): Flow<MaintenanceRecord?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMaintenance(record: MaintenanceRecord)

    @Query("SELECT * FROM maintenance WHERE status = 'submitted' ORDER BY date DESC")
    fun getSubmittedMaintenance(): Flow<List<MaintenanceRecord>>

    // Vehicles
    @Query("SELECT * FROM vehicles")
    fun getAllVehicles(): Flow<List<Vehicle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertVehicle(vehicle: Vehicle)

    @Query("DELETE FROM vehicles WHERE id = :id")
    suspend fun deleteVehicle(id: String)

    // Drivers
    @Query("SELECT * FROM drivers")
    fun getAllDrivers(): Flow<List<Driver>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDriver(driver: Driver)

    @Query("DELETE FROM drivers WHERE id = :id")
    suspend fun deleteDriver(id: String)

    @Query("SELECT * FROM drivers WHERE (name = :identity OR phone = :identity) AND password = :password LIMIT 1")
    suspend fun findDriver(identity: String, password: String): Driver?
}
