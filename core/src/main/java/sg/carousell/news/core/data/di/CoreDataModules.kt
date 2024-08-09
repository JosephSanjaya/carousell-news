package sg.carousell.news.core.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import sg.carousell.news.core.data.CarousellNewsHttpClient

@InstallIn(SingletonComponent::class)
@Module
class CoreDataModules {
    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            prettyPrint = true
            isLenient = false
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideKtorfit(httpClient: CarousellNewsHttpClient): Ktorfit {
        return httpClient.client
    }
}
