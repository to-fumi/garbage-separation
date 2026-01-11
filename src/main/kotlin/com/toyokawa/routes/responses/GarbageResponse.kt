package com.toyokawa.routes.responses

import com.toyokawa.data.domain.dto.Garbage
import com.toyokawa.data.domain.dto.GarbageCategory
import kotlinx.serialization.Serializable

@Serializable
data class GarbageResponse(
    val id: Long,
    val name: String,
    val disposalNotes: String? = null,
    val category: GarbageCategory,
)

fun Garbage.toResponse(): GarbageResponse = GarbageResponse(
    id = id,
    name = name,
    disposalNotes = disposalNotes,
    category = category,
)
