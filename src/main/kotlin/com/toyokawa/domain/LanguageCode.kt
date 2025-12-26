package com.toyokawa.domain

enum class LanguageCode(val code: String) {
    JA("ja"),
    EN("en");

    companion object {
        fun fromCode(code: String): LanguageCode? =
            entries.find { it.code == code }
    }
}