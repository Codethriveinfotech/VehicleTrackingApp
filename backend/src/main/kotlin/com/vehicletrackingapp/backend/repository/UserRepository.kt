package com.vehicletrackingapp.backend.repository

import com.vehicletrackingapp.backend.database.Users
import com.vehicletrackingapp.backend.models.User
import com.vehicletrackingapp.backend.utils.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

interface UserRepository {
    suspend fun createUser(user: User): User?
    suspend fun findById(id: String): User?
    suspend fun findByEmail(email: String): User?
    suspend fun findByPhone(phone: String): User?
    suspend fun findByIdentity(identity: String): User?
    suspend fun updateUser(user: User): Boolean
}

class UserRepositoryImpl : UserRepository {
    private fun resultRowToUser(row: ResultRow) = User(
        id = row[Users.id],
        name = row[Users.name],
        email = row[Users.email],
        phone = row[Users.phone],
        passwordHash = row[Users.passwordHash],
        createdAt = row[Users.createdAt],
        updatedAt = row[Users.updatedAt]
    )

    override suspend fun createUser(user: User): User? = dbQuery {
        val insertStatement = Users.insert {
            it[id] = user.id
            it[name] = user.name
            it[email] = user.email
            it[phone] = user.phone
            it[passwordHash] = user.passwordHash
            it[createdAt] = user.createdAt
            it[updatedAt] = user.updatedAt
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUser)
    }

    override suspend fun findById(id: String): User? = dbQuery {
        Users.select { Users.id eq id }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun findByEmail(email: String): User? = dbQuery {
        Users.select { Users.email eq email }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun findByPhone(phone: String): User? = dbQuery {
        Users.select { Users.phone eq phone }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun findByIdentity(identity: String): User? = dbQuery {
        Users.select { (Users.email eq identity) or (Users.phone eq identity) }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun updateUser(user: User): Boolean = dbQuery {
        Users.update({ Users.id eq user.id }) {
            it[name] = user.name
            it[email] = user.email
            it[phone] = user.phone
            it[updatedAt] = user.updatedAt
        } > 0
    }
}
