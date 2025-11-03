package com.toyokawa

import com.toyokawa.plugings.configureCORS
import com.toyokawa.plugings.configureRouting
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    configureCORS()
    configureRouting()
}
