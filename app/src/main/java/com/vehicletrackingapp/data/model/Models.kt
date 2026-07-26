package com.vehicletrackingapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "drivers")
data class Driver(
    @PrimaryKey val id: String,
    var name: String,
    var phone: String,
    var licenseNumber: String,
    var password: String,
    var photoUri: String? = null,
    var email: String = ""
)

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey val id: String,
    var number: String,
    var model: String,
    var imageUri: String? = null,
    var assignedDriverId: String? = null,
    var type: String = "Truck", // Truck, Van, etc.
    var registrationNumber: String = "",
    var fuelType: String = "Diesel",
    var status: String = "Active",
    var mileage: String = "0",
    var insuranceStatus: String = "Valid"
)

@Entity(tableName = "trips")
data class TripEntry(
    @PrimaryKey val id: String,
    val driverId: String,
    var vehicleId: String?,
    
    // Start Trip
    var startDate: String = "",
    var startTime: String = "",
    var startOdometer: String = "",
    var startOdometerPhotoUri: String? = null,
    var startVehiclePhotoUri: String? = null,
    var startVehiclePlatePhotoUri: String? = null,
    
    // End Trip
    var endDate: String = "",
    var endTime: String = "",
    var endOdometer: String = "",
    var endOdometerPhotoUri: String? = null,
    var endVehiclePhotoUri: String? = null,
    var endVehiclePlatePhotoUri: String? = null,
    
    // Details
    var sourceLocation: String = "",
    var destinationLocation: String = "",
    var fuelLevel: String = "Full",
    var tripPurpose: String = "Delivery",
    var notes: String = "",
    
    var status: String = "draft" // draft | submitted
)

@Entity(tableName = "maintenance")
data class MaintenanceRecord(
    @PrimaryKey val id: String,
    val vehicleId: String,
    val driverId: String,
    var maintenanceType: String = "", // Petrol, Wheel, Battery, etc.
    var description: String,
    var date: String,
    var time: String = "",
    var cost: String,
    var serviceNotes: String = "",
    var billImageUri: String? = null,
    var status: String = "draft", // draft | submitted
    var oilChangeDone: Boolean = false,
    var tyreStatusOk: Boolean = true,
    var batteryStatusOk: Boolean = true
)

enum class AppLanguage(val code: String, val label: String) {
    ENGLISH("en", "English"),
    TAMIL("ta", "தமிழ்"),
    HINDI("hi", "हिन्दी")
}
