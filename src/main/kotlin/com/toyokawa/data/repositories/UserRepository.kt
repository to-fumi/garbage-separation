package com.toyokawa.data.repositories

import com.toyokawa.data.domain.dto.Role
import com.toyokawa.data.domain.dto.User
import com.toyokawa.data.domain.tables.DatabaseFactory.dbQuery
import com.toyokawa.data.domain.tables.Users
import com.toyokawa.plugins.ITokenGenerator
import com.toyokawa.routes.requests.LoginRequest
import com.toyokawa.routes.requests.RegisterRequest
import com.toyokawa.data.repositories.interfaces.IUserRepository
import com.toyokawa.routes.responses.TokenResponse
import com.toyokawa.data.exceptions.ConflictException
import com.toyokawa.data.exceptions.NotFoundException
import com.toyokawa.data.exceptions.UnauthorizedException
import com.toyokawa.routes.responses.UserResponse
import com.toyokawa.routes.responses.toResponse
import io.ktor.util.logging.KtorSimpleLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.selectAll
import org.mindrot.jbcrypt.BCrypt

class UserRepository(
    private val tokenGenerator: ITokenGenerator,
) : IUserRepository {
    val logger = KtorSimpleLogger(this::class.java.name)

    override suspend fun getToken(dto: LoginRequest): TokenResponse {
        val user = dbQuery {
            Users
                .selectAll()
                .where { (Users.username eq dto.accountName) or (Users.email eq dto.accountName) }
                .map {
                    User(
                        id = it[Users.id],
                        username = it[Users.username],
                        email = it[Users.email],
                        passwordHash = it[Users.passwordHash],
                        role = Role.fromValue(it[Users.role]),
                    )
                }
                .singleOrNull()
        } ?: throw NotFoundException("User not found")

        if (!BCrypt.checkpw(dto.password, user.passwordHash)) {
            throw UnauthorizedException("Invalid username or password")
        }

        return TokenResponse(token = tokenGenerator.generateToken(user))
    }

    override suspend fun create(dto: RegisterRequest) {
        val existing = dbQuery {
            Users.selectAll()
                .where { (Users.username eq dto.username) or (Users.email eq dto.email) }
                .singleOrNull()
        }
        if (existing != null) throw ConflictException("Username or email already exists")

        val passwordHash = BCrypt.hashpw(dto.password, BCrypt.gensalt(12))

        val userId = dbQuery {
            Users.insert {
                it[Users.username] = dto.username
                it[Users.email] = dto.email
                it[Users.passwordHash] = passwordHash
                it[Users.role] = dto.role.value
            }[Users.id]
        }

        logger.info("Successfully created user with id: $userId")
    }

    override suspend fun findById(id: Int): UserResponse {
        val user = dbQuery {
            Users
                .selectAll()
                .where { Users.id eq id }
                .map {
                    User(
                        id = it[Users.id],
                        username = it[Users.username],
                        email = it[Users.email],
                        passwordHash = it[Users.passwordHash],
                        role = Role.fromValue(it[Users.role]),
                    )
                }
                .singleOrNull()
        } ?: throw NotFoundException("User not found")

        return user.toResponse()
    }
}
