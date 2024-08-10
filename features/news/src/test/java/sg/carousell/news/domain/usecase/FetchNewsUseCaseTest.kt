package sg.carousell.news.domain.usecase

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import sg.carousell.news.data.NewsRepo
import sg.carousell.news.domain.model.NewsDomain
import sg.carousell.news.utils.mockNewsData

class FetchNewsUseCaseTest : FunSpec({

    val repo = mockk<NewsRepo>()
    val useCase = FetchNewsUseCase(repo)
    val mockkNews = listOf(
        mockNewsData()
    )

    beforeTest {
        coEvery { repo.getNews() } returns mockkNews
    }


    test("invocation should return news from the repo and map it into news domain") {
        val result = useCase()
        result shouldBe mockkNews.map(::NewsDomain)
    }
})
