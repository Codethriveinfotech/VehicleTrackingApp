package com.vehicletrackingapp.backend.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.healthRoutes() {
    get("/health") {
        call.respond(mapOf("status" to "OK", "timestamp" to System.currentTimeMillis()))
    }
}
