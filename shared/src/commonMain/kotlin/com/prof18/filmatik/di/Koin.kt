package com.prof18.filmatik.di

import com.prof18.filmatik.data.MovieApiService
import com.prof18.filmatik.data.MovieApiServiceImpl
import com.prof18.filmatik.domain.MovieRepository
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appModule: Module): KoinApplication {
    return startKoin {
        modules(appModule, coreModule)
    }
}

private val coreModule = module {
    single<MovieApiService> {
        MovieApiServiceImpl(
            deviceLocale = get(),
            apiKey = get(),
        )
    }
    factory {
        MovieRepository(
            movieApiService = get(),
        )
    }
}