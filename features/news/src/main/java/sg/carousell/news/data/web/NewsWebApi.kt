package sg.carousell.news.data.web

import de.jensklingenberg.ktorfit.http.GET
import sg.carousell.news.data.model.NewsData

interface NewsWebApi {
    @GET("carousell_news.json")
    suspend fun getNews(): List<NewsData>
}
