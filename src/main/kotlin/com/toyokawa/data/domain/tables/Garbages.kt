package com.toyokawa.data.domain.tables

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentTimestamp
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object Garbages: Table("garbages") {
    val id = long("id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()
    val disposalNote = varchar("disposal_note", 255).nullable()
    val category = varchar("category", 30)
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
