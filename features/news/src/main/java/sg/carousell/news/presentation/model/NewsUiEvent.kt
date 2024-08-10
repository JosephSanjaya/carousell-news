package sg.carousell.news.presentation.model

import sg.carousell.news.domain.model.NewsDomain

sealed interface NewsUiEvent {
    data object OnFetchNews : NewsUiEvent
    data object ShowSortOption : NewsUiEvent
    data class OnNewsClick(val news: NewsDomain) : NewsUiEvent
    data object OnSortOptionDismiss : NewsUiEvent
    data class OnSortOptionSelect(val option: NewsSortType) : NewsUiEvent
}
