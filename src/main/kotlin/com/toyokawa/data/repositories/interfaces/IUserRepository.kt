package com.toyokawa.data.repositories.interfaces

import com.toyokawa.routes.requests.LoginRequest
import com.toyokawa.routes.requests.RegisterRequest
import com.toyokawa.routes.responses.TokenResponse
import com.toyokawa.routes.responses.UserResponse

interface IUserRepository {
    suspend fun getToken(dto: LoginRequest): TokenResponse
    suspend fun create(dto: RegisterRequest)
    suspend fun findById(id: Int): UserResponse
    suspend fun requireAdmin(id: Int)
}
