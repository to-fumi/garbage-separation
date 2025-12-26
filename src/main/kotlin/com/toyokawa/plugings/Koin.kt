package com.toyokawa.plugings

import com.toyokawa.data.repositories.GarbageRepository
import com.toyokawa.domain.usecases.GarbageUsecase
import com.toyokawa.domain.repositories.IGarbageRepository
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

        factory { GarbageUsecase() }
    }

    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
}
