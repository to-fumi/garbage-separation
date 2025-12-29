package com.toyokawa.data.db

import com.toyokawa.routes.dto.LanguageEnum
import org.jetbrains.exposed.sql.Table

object LanguageCodes: Table("language_codes") {
    val id = integer("id").autoIncrement()
    val code = enumeration<LanguageEnum>("code").uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}
