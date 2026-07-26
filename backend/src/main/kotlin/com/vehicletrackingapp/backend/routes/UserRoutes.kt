package com.vehicletrackingapp.backend.routes

import com.vehicletrackingapp.backend.dto.ApiResponse
import com.vehicletrackingapp.backend.dto.UserDto
import com.vehicletrackingapp.backend.repository.UserRepository
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(userRepository: UserRepository) {
    authenticate("auth-jwt") {
        route("/user") {
            get("/profile") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.subject ?: ""
                val user = userRepository.findById(userId)
                if (user != null) {
                    call.respond(ApiResponse.success(UserDto(user.id, user.name, user.email, user.phone)))
                } else {
                    call.respond(ApiResponse.error("User not found"))
                }
            }
        }
    }
}
