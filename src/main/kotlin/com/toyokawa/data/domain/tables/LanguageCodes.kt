package com.toyokawa.data.domain.tables

import org.jetbrains.exposed.sql.Table

const val MAX_CODE_LENGTH = 10

object LanguageCodes: Table("language_codes") {
    val id = integer("id").autoIncrement()
    val code = varchar("code", MAX_CODE_LENGTH).uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}
