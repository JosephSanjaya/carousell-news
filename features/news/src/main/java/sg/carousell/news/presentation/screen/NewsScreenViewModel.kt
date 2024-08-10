package sg.carousell.news.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sg.carousell.news.core.presentation.utils.IODispatcher
import sg.carousell.news.core.presentation.utils.safeLaunch
import sg.carousell.news.domain.usecase.FetchNewsUseCase
import sg.carousell.news.presentation.model.NewsSortType
import sg.carousell.news.presentation.model.NewsUiEvent
import sg.carousell.news.presentation.model.NewsUiState
import javax.inject.Inject

@HiltViewModel
class NewsScreenViewModel @Inject constructor(
    private val useCase: FetchNewsUseCase,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState get() = _uiState.asStateFlow()

    private val _uiEvent = Channel<NewsUiEvent>()
    val uiEvent
        get() = _uiEvent.receiveAsFlow()
            .onEach(::onEventSideEffect)

    fun onEvent(event: NewsUiEvent) = viewModelScope.launch {
        _uiEvent.send(event)
    }

    private fun onEventSideEffect(event: NewsUiEvent) {
        when (event) {
            NewsUiEvent.OnFetchNews -> onNewsFetched()
            NewsUiEvent.OnSortOptionDismiss -> _uiState.update { it.copy(isShowSortDialog = false) }
            is NewsUiEvent.OnSortOptionSelect -> {
                _uiState.update { it.copy(sortType = event.option) }
                onNewsFetched()
            }

            NewsUiEvent.ShowSortOption -> _uiState.update { it.copy(isShowSortDialog = true) }
            else -> {
                /** Do Nothing **/
            }
        }
    }

    private fun onNewsFetched() {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.safeLaunch(ioDispatcher, onError = { error ->
            _uiState.update { it.copy(isLoading = false, error = error.message.orEmpty()) }
        }) {
            val news = useCase.invoke().sortedBy {
                when (_uiState.value.sortType) {
                    NewsSortType.RECENT -> it.timeCreated
                    NewsSortType.POPULAR -> it.rank.toLong()
                }
            }
            _uiState.update { it.copy(isLoading = false, data = news) }
        }
    }
}
