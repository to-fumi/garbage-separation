package com.toyokawa.routes.requests

import com.toyokawa.data.domain.dto.GarbageCategory
import com.toyokawa.data.domain.dto.LanguageCode
import kotlinx.serialization.Serializable

@Serializable
data class UpsertGarbageRequest(
    val languageCode: LanguageCode,
    val name: String,
    val disposalNotes: String? = null,
    val category: GarbageCategory,
)
