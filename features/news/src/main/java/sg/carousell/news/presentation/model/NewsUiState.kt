package sg.carousell.news.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import sg.carousell.news.domain.model.NewsDomain

@Immutable
@Stable
data class NewsUiState(
    val isLoading: Boolean = false,
    val data: List<NewsDomain> = emptyList(),
    val error: String? = null,
    val isShowSortDialog: Boolean = false,
    val sortType: NewsSortType = NewsSortType.RECENT
)
