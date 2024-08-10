package sg.carousell.news.domain.usecase

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import sg.carousell.news.data.NewsRepo
import sg.carousell.news.data.model.NewsData
import sg.carousell.news.data.web.NewsWebApi
import sg.carousell.news.domain.model.NewsDomain

class FetchNewsUseCaseTest : FunSpec({

    val repo = mockk<NewsRepo>()
    val useCase = FetchNewsUseCase(repo)
    val mockkNews = listOf(
        NewsData(
            bannerUrl = "test",
            description = "test",
            id = "test",
            rank = 1,
            timeCreated = 1,
            title = "test",
        )
    )

    beforeTest {
        coEvery { repo.getNews() } returns mockkNews
    }


    test("getNews should return news from the API") {
        val result = useCase()
        result shouldBe mockkNews.map(::NewsDomain)
    }
})
