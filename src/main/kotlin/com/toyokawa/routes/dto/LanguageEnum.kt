package com.toyokawa.routes.dto

enum class LanguageEnum(val id: Int, val code: String) {
    JA(1, "ja"),
    EN(2, "en");

    companion object {
        fun fromCode(code: String): LanguageEnum =
            entries.find { it.code == code }
                ?: throw IllegalArgumentException("Unknown language code $code")
    }
}
