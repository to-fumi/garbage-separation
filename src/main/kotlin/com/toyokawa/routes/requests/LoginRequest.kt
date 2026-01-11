package com.toyokawa.routes.requests

import com.toyokawa.data.domain.dto.Role
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val accountName: String,
    val password: String,
)

@Serializable
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: Role,
)
