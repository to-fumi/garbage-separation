package com.toyokawa

import com.toyokawa.plugings.configureAuthentication
import com.toyokawa.plugings.configureCORS
import com.toyokawa.plugings.configureContentNegotiation
import com.toyokawa.plugings.configureDatabases
import com.toyokawa.plugings.configureOpenTelemetry
import com.toyokawa.plugings.configureRequestValidation
import com.toyokawa.plugings.configureRouting
import com.toyokawa.plugings.configureStatus
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
    configureRouting()
    configureStatus()
    configureOpenTelemetry()
}
