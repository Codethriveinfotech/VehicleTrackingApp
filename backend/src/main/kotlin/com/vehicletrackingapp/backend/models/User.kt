package com.vehicletrackingapp.backend.models

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val passwordHash: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
