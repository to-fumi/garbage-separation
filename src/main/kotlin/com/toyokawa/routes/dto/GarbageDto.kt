package com.toyokawa.routes.dto

import kotlinx.serialization.Serializable

@Serializable
data class GarbageDto(
    val id: Long,
    val name: String,
    val disposalNotes: String? = null,
    val category: GarbageCategory,
)

@Serializable
data class UpsertGarbageDto(
    val languageEnum: LanguageEnum,
    val name: String,
    val disposalNotes: String? = null,
    val category: GarbageCategory,
)

enum class GarbageCategory(val id: Int, val category: String) {
    BURNABLE(1, "burnable"),
    NON_BURNABLE(2, "non-burnable"),
    OVERSIZED(3, "oversized"),
    RECYCLABLE(4, "recyclable"),
    HAZARDOUS(5, "hazardous"),
    NOT_ACCEPTED(6, "not-accepted"),
    HOME_APPLIANCE_RECYCLING(7, "home-appliance-recycling"),
    DIRECT_DELIVERY(8, "direct-delivery");

    companion object {
        fun fromCategory(category: String): GarbageCategory =
            entries.find { it.category == category }
                ?: throw IllegalArgumentException("Unknown category: $category")
    }
}
