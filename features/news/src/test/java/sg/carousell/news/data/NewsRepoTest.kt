package sg.carousell.news.data

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import sg.carousell.news.data.web.NewsWebApi
import sg.carousell.news.utils.mockNewsData

class NewsRepoTest : FunSpec({

    val newsWebApi = mockk<NewsWebApi>()
    val newsRepo = NewsRepo(newsWebApi)
    val mockkNews = listOf(
        mockNewsData()
    )

    beforeTest {
        coEvery { newsWebApi.getNews() } returns mockkNews
    }

    test("getNews should return news from the API") {
        val result = newsRepo.getNews()
        result shouldBe mockkNews
    }
})
