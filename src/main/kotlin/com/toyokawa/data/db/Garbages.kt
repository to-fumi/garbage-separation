package com.toyokawa.data.db

import com.toyokawa.routes.dto.GarbageCategory
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

const val MAX_NAME_LENGTH = 100
const val MAX_DISPOSAL_NOTE_LENGTH = 255

object Garbages: Table("garbages") {
    val id = long("id").autoIncrement()
    val name = varchar("name", MAX_NAME_LENGTH).uniqueIndex()
    val disposalNote = varchar("disposalNote", MAX_DISPOSAL_NOTE_LENGTH).nullable()
    val category = enumeration<GarbageCategory>("category")
    val languageId = reference(
        name = "language_id",
        refColumn = LanguageCodes.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE,
        fkName = "fk_language_codes"
    )
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = timestamp("updated_at").nullable()

    override val primaryKey = PrimaryKey(id)
}
