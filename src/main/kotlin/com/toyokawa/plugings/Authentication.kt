package com.toyokawa.plugings

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.bearer
import io.ktor.util.logging.KtorSimpleLogger

fun Application.configureAuthentication() {
    val logger = KtorSimpleLogger(this::class.java.name)

    install(Authentication) {
        bearer("auth-bearer") {
            realm = "Access to the '/admin' path"
            authenticate { tokenCredential ->
                if (tokenCredential.token == "mock-token") {
                    UserIdPrincipal("Garbage")
                } else {
                    null
                }
            }
        }
    }
}