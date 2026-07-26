package com.vehicletrackingapp.util

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.vehicletrackingapp.data.model.TripEntry
import java.io.File
import java.io.FileWriter

object ExportUtils {
    fun exportTripsToCsv(context: Context, trips: List<TripEntry>) {
        try {
            val fileName = "fleet_report_${System.currentTimeMillis()}.csv"
            val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            if (!downloadsDir.exists()) downloadsDir.mkdirs()
            val file = File(downloadsDir, fileName)
            
            val writer = FileWriter(file)
            writer.write("Trip ID,Driver ID,Vehicle ID,Source,Destination,Start Date,Start Time,Start KM,End Date,End Time,End KM,Fuel,Purpose,Status\n")
            
            trips.forEach { trip ->
                writer.write("${trip.id},${trip.driverId},${trip.vehicleId},${trip.sourceLocation},${trip.destinationLocation},${trip.startDate},${trip.startTime},${trip.startOdometer},${trip.endDate},${trip.endTime},${trip.endOdometer},${trip.fuelLevel},${trip.tripPurpose},${trip.status}\n")
            }
            
            writer.flush()
            writer.close()
            
            Toast.makeText(context, "Report exported to Downloads: $fileName", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Export failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
