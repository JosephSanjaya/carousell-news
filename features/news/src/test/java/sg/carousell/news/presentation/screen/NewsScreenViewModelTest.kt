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
import sg.carousell.news.domain.usecase.FetchNewsUseCase
import sg.carousell.news.presentation.model.NewsSortType
import sg.carousell.news.presentation.model.NewsUiEvent
import sg.carousell.news.presentation.model.NewsUiState
import sg.carousell.news.utils.mockListNewsDomain

@OptIn(ExperimentalCoroutinesApi::class)
class NewsScreenViewModelTest : FunSpec({

    val useCase = mockk<FetchNewsUseCase>()
    val testDispatcher = UnconfinedTestDispatcher()
    lateinit var viewModel: NewsScreenViewModel

    beforeTest {
        Dispatchers.setMain(testDispatcher)
        viewModel = NewsScreenViewModel(useCase, testDispatcher)
    }

    afterTest {Dispatchers.resetMain()
        unmockkAll()
    }

    test("initial state should be correct") {
        viewModel.uiState.value shouldBe NewsUiState()
    }

    suspend fun testUiState(event: NewsUiEvent, expectedState: NewsUiState) {
        viewModel.uiEvent.test {
            viewModel.onEvent(event)
            viewModel.uiState.test {
                awaitItem() shouldBe expectedState
            }
            cancelAndConsumeRemainingEvents()
        }
    }

    test("OnFetchNews event should fetch news then update state") {
        val mockNews = mockListNewsDomain()
        coEvery { useCase.invoke() } returns mockNews
        testUiState(
            NewsUiEvent.OnFetchNews,
            NewsUiState(isLoading = false, data = mockNews)
        )
    }

    test("ShowSortOption should update state to show sort options") {
        testUiState(
            NewsUiEvent.ShowSortOption,
            NewsUiState(isShowSortDialog = true)
        )
    }

    test("OnSortOptionDismiss event should dismiss sort options") {
        viewModel.onEvent(NewsUiEvent.ShowSortOption)
        testUiState(
            NewsUiEvent.OnSortOptionDismiss,
            NewsUiState(isShowSortDialog = false)
        )
    }

    test("OnSortOptionSelect event should update state and refetch news") {
        val mockNews = mockListNewsDomain()
        coEvery { useCase.invoke() } returns mockNews
        testUiState(
            NewsUiEvent.OnSortOptionSelect(NewsSortType.POPULAR),
            NewsUiState(
                sortType = NewsSortType.POPULAR,
                isLoading = false,
                data = mockNews.sortedBy { it.rank.toLong() }
            )
        )
    }

    test("Error during OnFetchNews event should update state to show error") {
        val errorMessage = "Error fetching news"
        coEvery { useCase.invoke() } throws Throwable(errorMessage)
        testUiState(
            NewsUiEvent.OnFetchNews,
            NewsUiState(isLoading = false, error = errorMessage)
        )
    }

    test("onEvent method should emit events") {
        viewModel.uiEvent.test {
            viewModel.onEvent(NewsUiEvent.ShowSortOption)
            awaitItem() shouldBe NewsUiEvent.ShowSortOption

            viewModel.onEvent(NewsUiEvent.OnSortOptionSelect(NewsSortType.POPULAR))
            awaitItem() shouldBe NewsUiEvent.OnSortOptionSelect(NewsSortType.POPULAR)
        }
    }
})
