package com.toyokawa.domain.usecases

import com.toyokawa.data.repositories.GarbageRepository
import com.toyokawa.routes.dto.GarbageCategory
import com.toyokawa.routes.dto.GarbageDto
import io.ktor.util.logging.KtorSimpleLogger

class GarbageUsecase(
    private val garbageRepository: GarbageRepository,
) {
    val logger = KtorSimpleLogger(this::class.java.name)

    suspend fun getAll(): List<GarbageDto> {
        val garbages = garbageRepository.findAll()
        return listOf(
            GarbageDto(
                id = 1,
                name = "Garbage",
                disposalNotes = null,
                category = GarbageCategory.BURNABLE,
            ),
        )
    }

     suspend fun getById(id: Long): GarbageDto? {
        val garbage = garbageRepository.findById(id)
        return GarbageDto(
            id = id,
            name = "Garbage",
            disposalNotes = null,
            category = GarbageCategory.NON_BURNABLE,
)
    }

    suspend fun getByCategory(category: GarbageCategory): List<GarbageDto> {
        val garbages = garbageRepository.findByCategory(category)
        return listOf()
    }
}