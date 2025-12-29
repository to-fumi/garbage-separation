package com.toyokawa.data.repositories

import com.toyokawa.routes.dto.CreateGarbageDto
import com.toyokawa.routes.dto.GarbageCategory
import com.toyokawa.routes.dto.GarbageDto
import com.toyokawa.routes.dto.UpdateGarbageDto
import io.ktor.util.logging.KtorSimpleLogger

class GarbageRepository : IGarbageRepository {
    val logger = KtorSimpleLogger(this::class.java.name)

    override suspend fun findAll(): List<GarbageDto> {
        TODO("Not yet implemented")
    }

    override suspend fun findById(id: Long): GarbageDto {
        TODO("Not yet implemented")
    }

    override suspend fun findByCategory(category: GarbageCategory): List<GarbageDto> {
        TODO("Not yet implemented")
    }

    override suspend fun create(dto: CreateGarbageDto) {
        TODO("Not yet implemented")
    }

    override suspend fun update(dto: UpdateGarbageDto) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Long): Boolean {
        TODO("Not yet implemented")
    }
}
