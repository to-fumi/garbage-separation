package com.toyokawa.routes.responses

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val token: String,
)
