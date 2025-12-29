package com.toyokawa.routes.dto

enum class LanguageEnum(val code: String) {
    JA("ja"),
    EN("en");

    companion object {
        fun fromCode(code: String): LanguageEnum =
            entries.find { it.code == code }
                ?: throw IllegalArgumentException("Unknown language code $code")
    }
}
