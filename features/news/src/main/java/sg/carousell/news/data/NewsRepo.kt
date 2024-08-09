package sg.carousell.news.data

import javax.inject.Inject
import javax.inject.Singleton
import sg.carousell.news.data.web.NewsWebApi

@Singleton
class NewsRepo @Inject constructor(
    private val newsWebApi: NewsWebApi
) {
    suspend fun getNews() = newsWebApi.getNews()
}
