package com.toyokawa

import com.toyokawa.plugins.configureAuthentication
import com.toyokawa.plugins.configureCORS
import com.toyokawa.plugins.configureContentNegotiation
import com.toyokawa.plugins.configureDatabases
import com.toyokawa.plugins.configureKoin
import com.toyokawa.plugins.configureRequestValidation
import com.toyokawa.plugins.configureRouting
import com.toyokawa.plugins.configureStatusPages
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureAuthentication()
    configureContentNegotiation()
    configureCORS()
    configureDatabases()
    configureRequestValidation()
    configureKoin()
    configureRouting()
    configureStatusPages()

    // TODO: TBD
    // configureOpenTelemetry()
}
