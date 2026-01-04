package com.toyokawa.data.repositories

import com.toyokawa.data.db.DatabaseFactory.dbQuery
import com.toyokawa.data.db.Garbages
import com.toyokawa.routes.dto.GarbageCategory
import com.toyokawa.routes.dto.GarbageDto
import com.toyokawa.routes.dto.LanguageEnum
import com.toyokawa.routes.dto.UpsertGarbageDto
import io.ktor.util.logging.KtorSimpleLogger
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class GarbageRepository : IGarbageRepository {
    val logger = KtorSimpleLogger(this::class.java.name)

    override suspend fun findAll(
        lang: LanguageEnum,
        limit: Int,
        offset: Long,
    ): List<GarbageDto> {
        return dbQuery {
            Garbages
                .selectAll()
                .where { Garbages.languageCode eq lang.value }
                .limit(limit, offset)
                .map {
                    GarbageDto(
                        id = it[Garbages.id],
                        name = it[Garbages.name],
                        disposalNotes = it[Garbages.disposalNote],
                        category = GarbageCategory.fromCategory(it[Garbages.category]),
                    )
                }
        }
    }

    override suspend fun findById(lang: LanguageEnum, id: Long): GarbageDto? {
        return dbQuery {
            Garbages.selectAll()
                .where { Garbages.id eq id }
                .map {
                    GarbageDto(
                        id = it[Garbages.id],
                        name = it[Garbages.name],
                        disposalNotes = it[Garbages.disposalNote],
                        category = GarbageCategory.fromCategory(it[Garbages.category]),
                    )
                }
                .singleOrNull()
        }
    }

    override suspend fun findByCategory(
        lang: LanguageEnum,
        category: GarbageCategory,
        limit: Int,
        offset: Long,
    ): List<GarbageDto> {
        return dbQuery {
            Garbages
                .selectAll()
                .andWhere { Garbages.languageCode eq lang.value }
                .andWhere { Garbages.category eq category.value }
                .limit(limit, offset)
                .map {
                    GarbageDto(
                        id = it[Garbages.id],
                        name = it[Garbages.name],
                        disposalNotes = it[Garbages.disposalNote],
                        category = GarbageCategory.fromCategory(it[Garbages.category]),
                    )
                }
        }
    }

    override suspend fun create(dto: UpsertGarbageDto) {
        val garbageId = dbQuery {
            Garbages.insert {
                it[name] = dto.name
                it[disposalNote] = dto.disposalNotes
                it[category] = dto.category.value
                it[languageCode] = dto.languageEnum.value
            }[Garbages.id]
        }
        logger.info("Successfully created garbage with id: $garbageId")
    }

    override suspend fun update(id: Long, dto: UpsertGarbageDto) {
        dbQuery {
            Garbages.update({ Garbages.id eq id}) {
                it[name] = dto.name
                it[disposalNote] = dto.disposalNotes
                it[category] = dto.category.value
                it[languageCode] = dto.languageEnum.value
                it[updatedAt] = Clock.System.now()
            }
        }
        logger.info("Successfully updated garbage with id: $id")
    }

    override suspend fun delete(id: Long): Boolean {
        val result = dbQuery {
            Garbages.deleteWhere { Garbages.id eq Garbages.id } > 0
        }
        logger.info("Successfully deleted garbage with id: $id")
        return result
    }
}
