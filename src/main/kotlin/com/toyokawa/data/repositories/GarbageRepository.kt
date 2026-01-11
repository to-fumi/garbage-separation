package com.toyokawa.data.repositories

import com.toyokawa.data.domain.dto.Garbage
import com.toyokawa.data.domain.dto.GarbageCategory
import com.toyokawa.data.domain.dto.LanguageCode
import com.toyokawa.data.domain.tables.DatabaseFactory.dbQuery
import com.toyokawa.data.domain.tables.Garbages
import com.toyokawa.data.repositories.interfaces.IGarbageRepository
import com.toyokawa.routes.requests.UpsertGarbageRequest
import com.toyokawa.routes.responses.GarbageResponse
import com.toyokawa.routes.responses.toResponse
import io.ktor.server.plugins.NotFoundException
import io.ktor.util.logging.KtorSimpleLogger
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class GarbageRepository : IGarbageRepository {
    val logger = KtorSimpleLogger(this::class.java.name)

    override suspend fun findAll(
        lang: LanguageCode,
        limit: Int,
        offset: Long,
    ): List<GarbageResponse> {
        val garbages = dbQuery {
            Garbages
                .selectAll()
                .where { Garbages.languageCode eq lang.value }
                .limit(limit, offset)
                .map {
                    Garbage(
                        id = it[Garbages.id],
                        name = it[Garbages.name],
                        disposalNotes = it[Garbages.disposalNote],
                        category = GarbageCategory.fromValue(it[Garbages.category]),
                        languageCode = LanguageCode.fromValue(it[Garbages.languageCode]),
                    )
                }
        }
        return garbages.map { it.toResponse() }
    }

    override suspend fun findById(lang: LanguageCode, id: Long): GarbageResponse {
        val garbage = dbQuery {
            Garbages
                .selectAll()
                .where { Garbages.id eq id }
                .map {
                    Garbage(
                        id = it[Garbages.id],
                        name = it[Garbages.name],
                        disposalNotes = it[Garbages.disposalNote],
                        category = GarbageCategory.fromValue(it[Garbages.category]),
                        languageCode = LanguageCode.fromValue(it[Garbages.languageCode]),
                    )
                }
                .singleOrNull()
        } ?: throw NotFoundException("Garbage not found")

        return garbage.toResponse()
    }

    override suspend fun findByCategory(
        lang: LanguageCode,
        category: GarbageCategory,
        limit: Int,
        offset: Long,
    ): List<GarbageResponse> {
        val garbages = dbQuery {
            Garbages
                .selectAll()
                .where { (Garbages.languageCode eq lang.value) and (Garbages.category eq category.value) }
                .limit(limit, offset)
                .map {
                    Garbage(
                        id = it[Garbages.id],
                        name = it[Garbages.name],
                        disposalNotes = it[Garbages.disposalNote],
                        category = GarbageCategory.fromValue(it[Garbages.category]),
                        languageCode = LanguageCode.fromValue(it[Garbages.languageCode]),
                    )
                }
        }
        return garbages.map { it.toResponse() }
    }

    override suspend fun create(dto: UpsertGarbageRequest) {
        val garbageId = dbQuery {
            Garbages.insert {
                it[name] = dto.name
                it[disposalNote] = dto.disposalNotes
                it[category] = dto.category.value
                it[languageCode] = dto.languageCode.value
            }[Garbages.id]
        }
        logger.info("Successfully created garbage with id: $garbageId")
    }

    override suspend fun update(id: Long, dto: UpsertGarbageRequest) {
        dbQuery {
            Garbages.update({ Garbages.id eq id}) {
                it[name] = dto.name
                it[disposalNote] = dto.disposalNotes
                it[category] = dto.category.value
                it[languageCode] = dto.languageCode.value
                it[updatedAt] = Clock.System.now()
            }
        }
        logger.info("Successfully updated garbage with id: $id")
    }

    override suspend fun delete(id: Long) {
        val isDeleted = dbQuery { Garbages.deleteWhere { Garbages.id eq id } > 0 }
        if (!isDeleted) throw NotFoundException("Garbage not found")
        logger.info("Successfully deleted garbage with id: $id")
    }
}
