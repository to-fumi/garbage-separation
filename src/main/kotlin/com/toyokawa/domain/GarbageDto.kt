package com.toyokawa.domain

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
    val languageCode: LanguageCode,
    val name: String,
    val disposalNotes: String? = null,
    val category: GarbageCategory,
)

@Serializable
data class UpdateGarbageDto(
    val name: String,
    val disposalNotes: String? = null,
    val category: GarbageCategory,
)

enum class GarbageCategory {
    BURNABLE,
    NON_BURNABLE,
    OVERSIZED,
    RECYCLABLE,
    HAZARDOUS,
    NOT_ACCEPTED,
    HOME_APPLIANCE_RECYCLING,
    DIRECT_DELIVERY,
}
