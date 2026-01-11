package com.toyokawa.data.domain.tables

import org.jetbrains.exposed.sql.Table

object Roles: Table("roles") {
    val id = integer("id").autoIncrement()
    val role = varchar("role", 30).uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}
