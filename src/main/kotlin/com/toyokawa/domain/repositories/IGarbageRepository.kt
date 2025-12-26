package com.toyokawa.domain.repositories

import com.toyokawa.routes.dto.GarbageCategory
import com.toyokawa.routes.dto.GarbageDto

interface IGarbageRepository {
    suspend fun findAll(): List<String>
    suspend fun findById(id: Long): String?
    suspend fun findByCategory(category: GarbageCategory): List<String>
}