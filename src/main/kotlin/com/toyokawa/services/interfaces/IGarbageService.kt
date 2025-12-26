package com.toyokawa.services.interfaces

import com.toyokawa.domain.GarbageCategory
import com.toyokawa.domain.GarbageDto

interface IGarbageService {
    suspend fun getAll(): List<GarbageDto>
    suspend fun getById(id: Long): GarbageDto?
    suspend fun getByCategory(category: GarbageCategory): List<GarbageDto>
}