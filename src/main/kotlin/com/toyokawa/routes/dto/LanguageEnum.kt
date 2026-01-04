package com.toyokawa.routes.dto

enum class LanguageEnum(val value: String) {
    JA("ja"),
    EN("en");

    companion object {
        fun fromCode(value: String): LanguageEnum =
            entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown language code $value")
    }
}
