package com.toyokawa.data.db

import com.toyokawa.routes.dto.GarbageCategory
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

const val MAX_NAME_LENGTH = 100
const val MAX_DISPOSAL_NOTE_LENGTH = 255

object Garbages: Table("garbages") {
    val id = long("id").autoIncrement()
    val name = varchar("name", MAX_NAME_LENGTH).uniqueIndex()
    val disposalNote = varchar("disposal_note", MAX_DISPOSAL_NOTE_LENGTH).nullable()
    val category = enumeration<GarbageCategory>("category")
    val languageId = reference(
        name = "language_id",
        refColumn = LanguageCodes.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE,
        fkName = "fk_language_codes"
    )
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp())
    val updatedAt = timestamp("updated_at").nullable()

    override val primaryKey = PrimaryKey(id)
}
