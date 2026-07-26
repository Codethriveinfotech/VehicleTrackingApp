package com.vehicletrackingapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.vehicletrackingapp.navigation.AppNavGraph
import com.vehicletrackingapp.ui.theme.VehicleTrackingAppTheme
import com.vehicletrackingapp.util.LocaleHelper

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Apply locale at the content level to avoid early context wrapping crashes
            VehicleTrackingAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavGraph()
                }
            }
        }
    }
}
