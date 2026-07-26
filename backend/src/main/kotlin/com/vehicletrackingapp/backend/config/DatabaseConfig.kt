package com.vehicletrackingapp.backend.config

import com.vehicletrackingapp.backend.database.*
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

object DatabaseConfig {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun init(config: ApplicationConfig) {
        val url = config.property("database.url").getString()
        val user = config.propertyOrNull("database.user")?.getString() ?: ""
        val password = config.propertyOrNull("database.password")?.getString() ?: ""

        val hikariConfig = HikariConfig().apply {
            jdbcUrl = url
            username = user
            this.password = password
            driverClassName = "org.postgresql.Driver"
            maximumPoolSize = 10
            isReadOnly = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }

        val dataSource = HikariDataSource(hikariConfig)
        Database.connect(dataSource)

        transaction {
            SchemaUtils.create(Users, RefreshTokens, Vehicles, Trips, Maintenance)
            logger.info("Database initialized and schema created.")
        }
    }
}
