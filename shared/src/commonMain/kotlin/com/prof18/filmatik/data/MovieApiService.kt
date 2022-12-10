package com.prof18.filmatik.data

import co.touchlab.kermit.Logger
import com.prof18.filmatik.data.dto.PopularMoviesDTO
import com.prof18.filmatik.domain.DataResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

interface MovieApiService {
    suspend fun getPopularMovies(): DataResult<PopularMoviesDTO>

    companion object {
        const val BASE_PICTURE_ADDRESS = "https://image.tmdb.org/t/p/original"
    }
}

internal class MovieApiServiceImpl(
    val deviceLocale: DeviceLocale,
    val apiKey: ApiKey,
) : MovieApiService {

    private val baseUrl = "https://api.themoviedb.org/3"

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    override suspend fun getPopularMovies(): DataResult<PopularMoviesDTO> {
        return try {
            val response = httpClient.get("$baseUrl/movie/popular") {
                url {
                    parameters.append(
                        name = "api_key",
                        value = apiKey.key,
                    )
                    parameters.append(
                        name = "language",
                        value = "${deviceLocale.language}-${deviceLocale.country}",
                    )
                }
            }.body<PopularMoviesDTO>()
            DataResult.Success(response)
        } catch (e: Throwable) {
            Logger.e("Error during network request", e)
            DataResult.Error(e)
        }
    }
}