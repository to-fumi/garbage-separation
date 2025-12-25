package com.toyokawa.plugings

import io.ktor.server.application.Application
import io.ktor.util.logging.KtorSimpleLogger
import org.jetbrains.exposed.sql.Database

fun Application.configureDatabases() {
    val logger = KtorSimpleLogger(this::class.java.name)

    val url = environment.config.property("envConfig.database.url").getString()
    val user = environment.config.property("envConfig.database.user").getString()
    val password = environment.config.property("envConfig.database.password").getString()

    logger.info("Connecting to database...")
    val db = Database.connect(
        url = url,
        user = user,
        password = password,
    )
    logger.info("Connected to database.")
}
