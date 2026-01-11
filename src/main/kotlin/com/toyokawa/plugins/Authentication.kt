package com.toyokawa.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.toyokawa.data.domain.dto.User
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond
import io.ktor.util.logging.KtorSimpleLogger
import java.util.Date

fun Application.configureAuthentication() {
    val logger = KtorSimpleLogger(this::class.java.name)

    val secret = environment.config.property("jwt.secret").getString()
    val issuer = environment.config.property("jwt.issuer").getString()
    val audience = environment.config.property("jwt.audience").getString()
    val myRealm = environment.config.property("jwt.realm").getString()

    install(Authentication) {
        jwt("auth-jwt") {
            realm = myRealm
            verifier(JWT
                .require(Algorithm.HMAC256(secret))
                .withIssuer(issuer)
                .withAudience(audience)
                .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}

interface ITokenGenerator {
    fun generateToken(user: User): String
}

class TokenGenerator (
    val secret: String,
    private val issuer: String,
    private val audience: String,
    private val expiresIn: Long = 3600000
) : ITokenGenerator {
    private val algorithm = Algorithm.HMAC256(secret)

    override fun generateToken(user: User): String {
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("userId", user.id)
            .withClaim("username", user.username)
            .withClaim("email", user.email)
            .withClaim("role", user.role.name)
            .withExpiresAt(Date(System.currentTimeMillis() + expiresIn))
            .withIssuedAt(Date())
            .sign(algorithm)
    }
}
