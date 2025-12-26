package com.toyokawa.services

import com.toyokawa.services.interfaces.IGarbageRepository
import io.ktor.util.logging.KtorSimpleLogger

class GarbageRepository : IGarbageRepository {
    val logger = KtorSimpleLogger(this::class.java.name)
}