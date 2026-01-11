package com.toyokawa.data.domain.dto

data class Garbage(
    val id: Long,
    val name: String,
    val disposalNotes: String?,
    val category: GarbageCategory,
    val languageCode: LanguageCode,
)

enum class GarbageCategory(val value: String) {
    BURNABLE("burnable"),
    NON_BURNABLE("non-burnable"),
    OVERSIZED("oversized"),
    RECYCLABLE("recyclable"),
    HAZARDOUS("hazardous"),
    NOT_ACCEPTED("not-accepted"),
    HOME_APPLIANCE_RECYCLING("home-appliance-recycling"),
    DIRECT_DELIVERY("direct-delivery");

    companion object {
        fun fromValue(value: String): GarbageCategory =
            entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown category: $value")
    }
}
