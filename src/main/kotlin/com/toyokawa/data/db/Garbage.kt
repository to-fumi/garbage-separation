package com.toyokawa.data.db

import org.jetbrains.exposed.sql.Table

object Garbages: Table("garbages") {
    val id = long("id").autoIncrement()
    val name = varchar("name", 128).uniqueIndex()
    val disposalNote = varchar("disposalNote", 255).nullable()
    val category = varchar("category", 32).uniqueIndex()
}
