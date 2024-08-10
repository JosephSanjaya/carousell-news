package sg.carousell.news.presentation.screen

import app.cash.turbine.test
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import sg.carousell.news.domain.model.NewsDomain
import sg.carousell.news.domain.usecase.FetchNewsUseCase
import sg.carousell.news.presentation.model.NewsSortType
import sg.carousell.news.presentation.model.NewsUiEvent
import sg.carousell.news.presentation.model.NewsUiState

@OptIn(ExperimentalCoroutinesApi::class)
class NewsScreenViewModelTest : FunSpec({

    val useCase = mockk<FetchNewsUseCase>()
    val testDispatcher = UnconfinedTestDispatcher()
    lateinit var viewModel: NewsScreenViewModel

    beforeTest {
        Dispatchers.setMain(testDispatcher)
        viewModel = NewsScreenViewModel(useCase, testDispatcher)
    }

    afterTest {
        Dispatchers.resetMain()
        unmockkAll()
    }

    test("initial state should be correct") {
        viewModel.uiState.value shouldBe NewsUiState()
    }

    test("fetching news should update state correctly") {
        val mockNews = listOf(
            NewsDomain(
                bannerUrl = "https://duckduckgo.com/?q=veri",
                description = "tritani",
                id = "te",
                rank = 1,
                timeCreated = 1000L,
                title = "News 1"
            ),
            NewsDomain(
                bannerUrl = "https://duckduckgo.com/?q=veri",
                description = "tritani",
                id = "te",
                rank = 2,
                timeCreated = 2000L,
                title = "News 2"
            )
        )
        coEvery { useCase.invoke() } returns mockNews

        viewModel.uiEvent.test {
            viewModel.onEvent(NewsUiEvent.OnFetchNews)
            viewModel.uiState.test {
                awaitItem() shouldBe NewsUiState(isLoading = false, data = mockNews)
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    test("showing sort options should update state") {
        viewModel.uiEvent.test {
            viewModel.onEvent(NewsUiEvent.ShowSortOption)
            viewModel.uiState.test {
                awaitItem() shouldBe NewsUiState(isShowSortDialog = true)
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    test("dismissing sort options should update state") {
        viewModel.uiEvent.test {
            viewModel.onEvent(NewsUiEvent.ShowSortOption)
            viewModel.uiState.test {
                awaitItem() shouldBe NewsUiState(isShowSortDialog = true)
            }
            viewModel.onEvent(NewsUiEvent.OnSortOptionDismiss)
            viewModel.uiState.test {
                awaitItem() shouldBe NewsUiState(isShowSortDialog = false)
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    test("selecting sort option should update state and fetch news") {
        val mockNews = listOf(
            NewsDomain(
                bannerUrl = "https://duckduckgo.com/?q=veri",
                description = "tritani",
                id = "te",
                rank = 1,
                timeCreated = 1000L,
                title = "News 1"
            ),
            NewsDomain(
                bannerUrl = "https://duckduckgo.com/?q=veri",
                description = "tritani",
                id = "te",
                rank = 2,
                timeCreated = 2000L,
                title = "News 2"
            )
        )
        coEvery { useCase.invoke() } returns mockNews

        viewModel.uiEvent.test {
            viewModel.onEvent(NewsUiEvent.OnSortOptionSelect(NewsSortType.POPULAR))
            viewModel.uiState.test {
                awaitItem() shouldBe NewsUiState(
                    sortType = NewsSortType.POPULAR,
                    isLoading = false,
                    data = mockNews.sortedBy { it.rank.toLong() }
                )
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    test("error during news fetch should update state") {
        val errorMessage = "Error fetching news"
        val exception = Throwable(errorMessage)
        coEvery { useCase.invoke() } throws exception

        viewModel.uiEvent.test {
            viewModel.onEvent(NewsUiEvent.OnFetchNews)
            viewModel.uiState.test {
                awaitItem() shouldBe NewsUiState(isLoading = false, error = errorMessage)
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    test("uiEvent flow should emit events") {
        viewModel.uiEvent.test {
            viewModel.onEvent(NewsUiEvent.ShowSortOption)
            awaitItem() shouldBe NewsUiEvent.ShowSortOption

            viewModel.onEvent(NewsUiEvent.OnSortOptionSelect(NewsSortType.POPULAR))
            awaitItem() shouldBe NewsUiEvent.OnSortOptionSelect(NewsSortType.POPULAR)
        }
    }
})
