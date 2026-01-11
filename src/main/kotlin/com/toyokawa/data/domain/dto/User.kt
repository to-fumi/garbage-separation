package com.toyokawa.data.domain.dto

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val passwordHash: String,
    val role: Role,
)

enum class Role(val value: String) {
    USER("user"),
    ADMIN("admin");

    companion object {
        fun fromValue(value: String): Role =
            entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown role: $value")
    }
}
