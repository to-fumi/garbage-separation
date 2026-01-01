package com.toyokawa.data.repositories

import com.toyokawa.routes.dto.GarbageCategory
import com.toyokawa.routes.dto.GarbageDto
import com.toyokawa.routes.dto.LanguageEnum
import com.toyokawa.routes.dto.UpsertGarbageDto

interface IGarbageRepository {
    suspend fun findAll(lang: LanguageEnum, limit: Int, offset: Long): List<GarbageDto>
    suspend fun findById(lang: LanguageEnum, id: Long): GarbageDto?
    suspend fun findByCategory(lang: LanguageEnum, category: GarbageCategory, limit: Int, offset: Long): List<GarbageDto>
    suspend fun create(dto: UpsertGarbageDto)
    suspend fun update(id: Long, dto: UpsertGarbageDto)
    suspend fun delete(id: Long): Boolean
}
