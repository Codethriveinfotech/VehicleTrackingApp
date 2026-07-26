package com.vehicletrackingapp.backend.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.config.*
import java.util.*

class JwtConfig(config: ApplicationConfig) {
    private val secret = System.getenv("JWT_SECRET") ?: config.property("jwt.secret").getString()
    private val issuer = System.getenv("JWT_ISSUER") ?: config.property("jwt.issuer").getString()
    private val audience = System.getenv("JWT_AUDIENCE") ?: config.property("jwt.audience").getString()
    private val algorithm = Algorithm.HMAC256(secret)

    val verifier = JWT.require(algorithm)
        .withIssuer(issuer)
        .withAudience(audience)
        .build()

    fun generateAccessToken(userId: String): String = JWT.create()
        .withIssuer(issuer)
        .withAudience(audience)
        .withSubject(userId)
        .withExpiresAt(Date(System.currentTimeMillis() + 3600000)) // 1 hour
        .sign(algorithm)

    fun generateRefreshToken(): String = UUID.randomUUID().toString()
}
