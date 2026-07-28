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
            
            val content = StringBuilder()
            content.append("Trip ID,Driver ID,Vehicle ID,Source,Destination,Start Date,Start Time,Start KM,End Date,End Time,End KM,Fuel,Purpose,Status\n")
            trips.forEach { trip ->
                content.append("${trip.id},${trip.driverId},${trip.vehicleId},${trip.sourceLocation},${trip.destinationLocation},${trip.startDate},${trip.startTime},${trip.startOdometer},${trip.endDate},${trip.endTime},${trip.endOdometer},${trip.fuelLevel},${trip.tripPurpose},${trip.status}\n")
            }

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                val resolver = context.contentResolver
                val contentValues = android.content.ContentValues().apply {
                    put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "text/csv")
                    put(android.provider.MediaStore.MediaColumns.RELATIVE_PATH, android.os.Environment.DIRECTORY_DOWNLOADS)
                }
                val uri = resolver.insert(android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                if (uri != null) {
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        outputStream.write(content.toString().toByteArray())
                    }
                    Toast.makeText(context, "Report exported to Downloads folder", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Failed to create file", Toast.LENGTH_SHORT).show()
                }
            } else {
                val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) downloadsDir.mkdirs()
                val file = java.io.File(downloadsDir, fileName)
                try {
                    val writer = java.io.FileWriter(file)
                    writer.write(content.toString())
                    writer.flush()
                    writer.close()
                    Toast.makeText(context, "Report exported to Downloads: $fileName", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    // Fallback to internal app storage if no permission
                    val fallbackDir = context.getExternalFilesDir(android.os.Environment.DIRECTORY_DOWNLOADS)
                    val fallbackFile = java.io.File(fallbackDir, fileName)
                    val writer = java.io.FileWriter(fallbackFile)
                    writer.write(content.toString())
                    writer.flush()
                    writer.close()
                    Toast.makeText(context, "Report exported to App Files: $fileName", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Export failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
