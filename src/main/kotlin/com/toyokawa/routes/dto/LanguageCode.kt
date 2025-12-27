package com.toyokawa.routes.dto

enum class LanguageCode(val code: String) {
    JA("ja"),
    EN("en");

    companion object {
        fun fromCode(code: String): LanguageCode =
            entries.find { it.code == code }
                ?: throw IllegalArgumentException("Unknown language code $code")
    }
}
