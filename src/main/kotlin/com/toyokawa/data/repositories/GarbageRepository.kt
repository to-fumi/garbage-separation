package com.toyokawa.data.repositories

import com.toyokawa.domain.repositories.IGarbageRepository
import com.toyokawa.routes.dto.GarbageCategory
import io.ktor.util.logging.KtorSimpleLogger

class GarbageRepository : IGarbageRepository {
    val logger = KtorSimpleLogger(this::class.java.name)

    override suspend fun findAll(): List<String> {
        return listOf("Mock Garbage 1", "Mock Garbage 2", "Mock Garbage 3")
    }

    override suspend fun findById(id: Long): String? {
        return "Mock Garbage $id"
    }

    override suspend fun findByCategory(category: GarbageCategory): List<String> {
        return listOf("Mock Garbage 1")
    }
}