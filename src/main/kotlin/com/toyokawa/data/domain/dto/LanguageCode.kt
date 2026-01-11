package com.toyokawa.data.domain.dto

enum class LanguageCode(val value: String) {
    JA("ja"),
    EN("en");

    companion object {
        fun fromValue(value: String): LanguageCode =
            entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown language code $value")
    }
}
