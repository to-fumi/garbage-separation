package com.toyokawa.plugings

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.Application
import io.ktor.util.logging.KtorSimpleLogger
import org.flywaydb.core.Flyway
import org.flywaydb.core.api.FlywayException
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    val logger = KtorSimpleLogger(this::class.java.name)

    val dataSource = HikariDataSource (
        HikariConfig().apply {
            jdbcUrl = environment.config.property("envConfig.database.url").getString()
            driverClassName = environment.config.property("envConfig.database.className").getString()
            username = environment.config.property("envConfig.database.user").getString()
            password = environment.config.property("envConfig.database.password").getString()
            maximumPoolSize = 3
            isReadOnly = false
            transactionIsolation = "TRANSACTION_READ_COMMITTED"
            validate()
        }
    )

    try {
        val flyway = Flyway.configure()
            .dataSource(dataSource)
            .load()
        flyway.migrate()
    } catch (ex: FlywayException) {
        logger.error("Failed to migrate database", ex)
    }

    Database.connect(datasource = dataSource)

    logger.info("Connected to database.")
}
