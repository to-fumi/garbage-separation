package com.toyokawa.plugings

import com.toyokawa.services.GarbageRepository
import com.toyokawa.services.GarbageService
import com.toyokawa.services.interfaces.IGarbageRepository
import com.toyokawa.services.interfaces.IGarbageService
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {

    val appModule = module {
        singleOf(::GarbageService) { bind<IGarbageService>() }
        singleOf(::GarbageRepository) { bind<IGarbageRepository>() }
    }

    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}
