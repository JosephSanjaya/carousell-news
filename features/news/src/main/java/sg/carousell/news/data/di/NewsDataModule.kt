package sg.carousell.news.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import de.jensklingenberg.ktorfit.Ktorfit
import javax.inject.Singleton
import sg.carousell.news.data.web.NewsWebApi
import sg.carousell.news.data.web.createNewsWebApi

@InstallIn(SingletonComponent::class)
@Module
class NewsDataModule {
    @Provides
    @Singleton
    fun provideNewsWebApi(ktorfit: Ktorfit): NewsWebApi {
        return ktorfit.createNewsWebApi()
    }
}
