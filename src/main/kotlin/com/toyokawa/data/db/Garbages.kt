package com.toyokawa.data.db

import com.toyokawa.routes.dto.GarbageCategory
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

object Garbages: Table("garbages") {
    val id = long("id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()
    val disposalNote = varchar("disposalNote", 255).nullable()
    val category = enumeration<GarbageCategory>("category")
    val languageId = reference(
        name = "language_id",
        refColumn = LanguageCodes.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE,
        fkName = "fk_language_codes"
    )
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    val updatedAt = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}
