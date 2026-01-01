package com.toyokawa.plugins

import com.toyokawa.data.repositories.GarbageRepository
import com.toyokawa.data.repositories.IGarbageRepository
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
    }

    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}
