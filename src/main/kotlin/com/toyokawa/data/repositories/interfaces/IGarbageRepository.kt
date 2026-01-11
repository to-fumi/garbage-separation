package com.toyokawa.data.repositories.interfaces

import com.toyokawa.data.domain.dto.GarbageCategory
import com.toyokawa.data.domain.dto.LanguageCode
import com.toyokawa.routes.requests.UpsertGarbageRequest
import com.toyokawa.routes.responses.GarbageResponse

interface IGarbageRepository {
    suspend fun findAll(lang: LanguageCode, limit: Int, offset: Long): List<GarbageResponse>
    suspend fun findById(lang: LanguageCode, id: Long): GarbageResponse
    suspend fun findByCategory(lang: LanguageCode, category: GarbageCategory, limit: Int, offset: Long): List<GarbageResponse>
    suspend fun create(dto: UpsertGarbageRequest)
    suspend fun update(id: Long, dto: UpsertGarbageRequest)
    suspend fun delete(id: Long)
}
