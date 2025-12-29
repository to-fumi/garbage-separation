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
data class CreateGarbageDto(
    val languageEnum: LanguageEnum,
    val name: String,
    val disposalNotes: String? = null,
    val category: GarbageCategory,
)

@Serializable
data class UpdateGarbageDto(
    val id: Long,
    val languageEnum: LanguageEnum,
    val name: String,
    val disposalNotes: String? = null,
    val category: GarbageCategory,
)

enum class GarbageCategory(val id: Int) {
    BURNABLE(1),
    NON_BURNABLE(2),
    OVERSIZED(3),
    RECYCLABLE(4),
    HAZARDOUS(5),
    NOT_ACCEPTED(6),
    HOME_APPLIANCE_RECYCLING(7),
    DIRECT_DELIVERY(8),
}
