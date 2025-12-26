package com.toyokawa.services

import com.toyokawa.domain.GarbageCategory
import com.toyokawa.domain.GarbageDto
import com.toyokawa.services.interfaces.IGarbageService
import io.ktor.util.logging.KtorSimpleLogger

class GarbageService : IGarbageService {
    val logger = KtorSimpleLogger(this::class.java.name)

    override suspend fun getAll(): List<GarbageDto> {
        return listOf(
            GarbageDto(
                id = 1,
                name = "Garbage",
                disposalNotes = null,
                category = GarbageCategory.BURNABLE,
            ),
        )
    }

    override suspend fun getById(id: Long): GarbageDto? {
        return GarbageDto(
            id = id,
            name = "Garbage",
            disposalNotes = null,
            category = GarbageCategory.NON_BURNABLE,
        )
    }

    override suspend fun getByCategory(category: GarbageCategory): List<GarbageDto> {
        return listOf()
    }
}
