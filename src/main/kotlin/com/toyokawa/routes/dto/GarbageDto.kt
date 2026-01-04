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
        fun fromCategory(value: String): GarbageCategory =
            entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown category: $value")
    }
}
