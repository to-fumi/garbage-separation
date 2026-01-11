package com.toyokawa.routes.responses

import com.toyokawa.data.domain.dto.Role
import com.toyokawa.data.domain.dto.User
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val username: String,
    val email: String,
    val role: Role,
)

fun User.toResponse(): UserResponse = UserResponse(
    id = id,
    username = username,
    email = email,
    role = role,
)
