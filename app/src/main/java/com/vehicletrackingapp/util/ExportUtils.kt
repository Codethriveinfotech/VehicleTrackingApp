package com.vehicletrackingapp.util

import android.content.Context
import android.os.Environment
import android.widget.Toast
import com.vehicletrackingapp.data.model.TripEntry
import java.io.File
import java.io.FileWriter

object ExportUtils {
    fun exportTripsToPdf(context: Context, trips: List<TripEntry>) {
        try {
            val fileName = "fleet_report_${System.currentTimeMillis()}.pdf"
            val pdfDocument = android.graphics.pdf.PdfDocument()
            val paint = android.graphics.Paint()
            val titlePaint = android.graphics.Paint()
            
            val pageInfo = android.graphics.pdf.PdfDocument.PageInfo.Builder(792, 1120, 1).create() // A4 size
            var page = pdfDocument.startPage(pageInfo)
            var canvas = page.canvas
            
            titlePaint.typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
            titlePaint.textSize = 24f
            titlePaint.color = android.graphics.Color.BLACK
            titlePaint.textAlign = android.graphics.Paint.Align.CENTER
            canvas.drawText("Fleet Activity Report", 396f, 60f, titlePaint)
            
            paint.typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
            paint.textSize = 14f
            paint.color = android.graphics.Color.DKGRAY
            
            // Draw Table Headers
            val startY = 120f
            var currentY = startY
            val colX = floatArrayOf(40f, 180f, 320f, 460f, 560f, 660f)
            val headers = arrayOf("Date", "Source", "Destination", "Start KM", "End KM", "Status")
            
            for (i in headers.indices) {
                canvas.drawText(headers[i], colX[i], currentY, paint)
            }
            
            // Draw Line
            paint.strokeWidth = 2f
            canvas.drawLine(40f, currentY + 10f, 752f, currentY + 10f, paint)
            
            paint.typeface = android.graphics.Typeface.create(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.NORMAL)
            paint.textSize = 12f
            paint.color = android.graphics.Color.BLACK
            paint.strokeWidth = 0f
            
            currentY += 30f
            
            for (trip in trips) {
                if (currentY > 1050f) {
                    pdfDocument.finishPage(page)
                    page = pdfDocument.startPage(pageInfo)
                    canvas = page.canvas
                    currentY = 60f
                }
                
                val dateStr = "${trip.startDate} ${trip.startTime}"
                val src = if (trip.sourceLocation.length > 15) trip.sourceLocation.take(15) + "..." else trip.sourceLocation
                val dest = if (trip.destinationLocation.length > 15) trip.destinationLocation.take(15) + "..." else trip.destinationLocation
                
                canvas.drawText(dateStr, colX[0], currentY, paint)
                canvas.drawText(src, colX[1], currentY, paint)
                canvas.drawText(dest, colX[2], currentY, paint)
                canvas.drawText(trip.startOdometer, colX[3], currentY, paint)
                canvas.drawText(trip.endOdometer, colX[4], currentY, paint)
                canvas.drawText(trip.status.uppercase(), colX[5], currentY, paint)
                
                currentY += 25f
            }
            
            pdfDocument.finishPage(page)
            
            val resolver = context.contentResolver
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                val contentValues = android.content.ContentValues().apply {
                    put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                    put(android.provider.MediaStore.MediaColumns.RELATIVE_PATH, android.os.Environment.DIRECTORY_DOWNLOADS)
                }
                val uri = resolver.insert(android.provider.MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
                if (uri != null) {
                    resolver.openOutputStream(uri)?.use { outputStream ->
                        pdfDocument.writeTo(outputStream)
                    }
                    android.widget.Toast.makeText(context, "PDF Report exported to Downloads folder", android.widget.Toast.LENGTH_LONG).show()
                } else {
                    android.widget.Toast.makeText(context, "Failed to create file", android.widget.Toast.LENGTH_SHORT).show()
                }
            } else {
                val downloadsDir = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_DOWNLOADS)
                if (!downloadsDir.exists()) downloadsDir.mkdirs()
                val file = java.io.File(downloadsDir, fileName)
                try {
                    val outputStream = java.io.FileOutputStream(file)
                    pdfDocument.writeTo(outputStream)
                    outputStream.close()
                    android.widget.Toast.makeText(context, "PDF Report exported to Downloads: $fileName", android.widget.Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    val fallbackDir = context.getExternalFilesDir(android.os.Environment.DIRECTORY_DOWNLOADS)
                    val fallbackFile = java.io.File(fallbackDir, fileName)
                    val outputStream = java.io.FileOutputStream(fallbackFile)
                    pdfDocument.writeTo(outputStream)
                    outputStream.close()
                    android.widget.Toast.makeText(context, "PDF Report exported to App Files: $fileName", android.widget.Toast.LENGTH_LONG).show()
                }
            }
            pdfDocument.close()
        } catch (e: Exception) {
            e.printStackTrace()
            android.widget.Toast.makeText(context, "Export failed: ${e.message}", android.widget.Toast.LENGTH_SHORT).show()
        }
    }

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
