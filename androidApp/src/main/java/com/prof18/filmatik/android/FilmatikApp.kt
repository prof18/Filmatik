package com.prof18.filmatik.android

import android.app.Application
import com.prof18.filmatik.data.ApiKey
import com.prof18.filmatik.data.DeviceLocale
import com.prof18.filmatik.di.initKoin
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.Locale

class FilmatikApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                viewModel {
                    MainViewModel(
                        movieRepository = get()
                    )
                }

                single {
                    ApiKey(BuildConfig.TMDB_KEY)
                }

                single {
                    val currentLocale = Locale.getDefault()
                    DeviceLocale(
                        country = currentLocale.country,
                        language = currentLocale.language,
                    )
                }
            }
        )
    }
}
