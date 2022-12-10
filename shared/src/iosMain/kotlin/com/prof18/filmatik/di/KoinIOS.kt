package com.prof18.filmatik.di

import com.prof18.filmatik.data.ApiKey
import com.prof18.filmatik.data.DeviceLocale
import com.prof18.filmatik.domain.MovieRepository
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.dsl.module

fun initKoinIos(
    apiKey: ApiKey,
    deviceLocale: DeviceLocale,
): KoinApplication = initKoin(
    module {
        single { apiKey }
        single { deviceLocale }
    }
)

@Suppress("unused") // Called from Swift
object KotlinDependencies : KoinComponent {
    fun getMovieRepository() = getKoin().get<MovieRepository>()
}