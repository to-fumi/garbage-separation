package com.toyokawa.plugins

import com.toyokawa.data.repositories.GarbageRepository
import com.toyokawa.data.repositories.interfaces.IGarbageRepository
import com.toyokawa.data.repositories.interfaces.IUserRepository
import com.toyokawa.data.repositories.UserRepository
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {

    val appModule = module {
        singleOf(::GarbageRepository) { bind<IGarbageRepository>() }
        singleOf(::UserRepository) { bind<IUserRepository>() }
        single<ITokenGenerator> {
            TokenGenerator(
                secret = environment.config.property("jwt.secret").getString(),
                issuer = environment.config.property("jwt.issuer").getString(),
                audience = environment.config.property("jwt.audience").getString(),
                expiresIn = environment.config.propertyOrNull("jwt.expiresIn")
                    ?.getString()?.toLongOrNull() ?: 3600000,
            )
        }
    }

    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}
