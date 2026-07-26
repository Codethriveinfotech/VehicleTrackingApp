package com.vehicletrackingapp

import android.app.Application
import android.util.Log
import com.vehicletrackingapp.data.repo.AppRepository
import com.vehicletrackingapp.util.LocaleHelper

class VehicleTrackingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            // Initialize locale immediately
            LocaleHelper.init(this)
            
            // Initialize repository (DB building moved to background thread inside init)
            AppRepository.init(this)
            
            // Start background sync
            AppRepository.syncPendingData()
            
            Log.d("VehicleTrackingApp", "Application initialized successfully.")
        } catch (e: Exception) {
            Log.e("VehicleTrackingApp", "Initialization crash", e)
        }
    }
}
