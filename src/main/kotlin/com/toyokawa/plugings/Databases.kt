package com.toyokawa.plugings

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import io.ktor.util.logging.KtorSimpleLogger
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    val logger = KtorSimpleLogger(this::class.java.name)

    val config = HikariConfig().apply {
        jdbcUrl = environment.config.property("envConfig.database.url").getString()
        driverClassName = environment.config.property("envConfig.database.className").getString()
        username = environment.config.property("envConfig.database.user").getString()
        password = environment.config.property("envConfig.database.password").getString()
        maximumPoolSize = 3
        isReadOnly = false
        transactionIsolation = "TRANSACTION_SERIALIZABLE"
    }

    val dataSource = HikariDataSource(config)

    val db = Database.connect(datasource = dataSource)

    logger.info("Connected to database.")
}
