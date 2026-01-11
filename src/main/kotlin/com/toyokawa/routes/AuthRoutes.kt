package com.toyokawa.routes

import com.toyokawa.routes.requests.LoginRequest
import com.toyokawa.routes.requests.RegisterRequest
import com.toyokawa.data.repositories.interfaces.IUserRepository
import com.toyokawa.routes.extensions.requireUserId
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import org.koin.ktor.ext.get

fun Route.authRoute() {
    val userRepository = get<IUserRepository>()

    post("/login") {
        val login = call.receive<LoginRequest>()
        val token = userRepository.getToken(login)
        call.respond(token)
    }
    authenticate("auth-jwt") {
        post("/register") {
            val register = call.receive<RegisterRequest>()
            userRepository.create(register)
            call.respond(HttpStatusCode.Created)
        }
    }
}
