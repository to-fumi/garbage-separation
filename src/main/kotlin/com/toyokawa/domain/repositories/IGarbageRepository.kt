package com.toyokawa.domain.repositories

import com.toyokawa.routes.dto.CreateGarbageDto
import com.toyokawa.routes.dto.GarbageCategory
import com.toyokawa.routes.dto.GarbageDto
import com.toyokawa.routes.dto.LanguageCode
import com.toyokawa.routes.dto.UpdateGarbageDto

interface IGarbageRepository {
    suspend fun findAll(): List<GarbageDto>
    suspend fun findById(id: Long): GarbageDto
    suspend fun findByCategory(category: GarbageCategory): List<GarbageDto>
    suspend fun create(dto: CreateGarbageDto)
    suspend fun update(id: Long, lang: LanguageCode, dto: UpdateGarbageDto)
    suspend fun delete(id: Long): Boolean
}
