package com.toyokawa.data.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

const val MAX_NAME_LENGTH = 100
const val MAX_DISPOSAL_NOTE_LENGTH = 255
const val MAX_CATEGORY_LENGTH = 30

object Garbages: Table("garbages") {
    val id = long("id").autoIncrement()
    val name = varchar("name", MAX_NAME_LENGTH).uniqueIndex()
    val disposalNote = varchar("disposal_note", MAX_DISPOSAL_NOTE_LENGTH).nullable()
    val category = varchar("category", MAX_CATEGORY_LENGTH)
    val languageCode = reference(
        name = "language_code",
        refColumn = LanguageCodes.code,
        onDelete = ReferenceOption.RESTRICT,
        onUpdate = ReferenceOption.CASCADE,
        fkName = "fk_language_codes"
    )
    val createdAt = timestamp("created_at").defaultExpression(CurrentTimestamp())
    val updatedAt = timestamp("updated_at").nullable()

    override val primaryKey = PrimaryKey(id)
}
