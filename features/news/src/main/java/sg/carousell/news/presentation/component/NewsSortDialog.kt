package sg.carousell.news.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import sg.carousell.news.core.presentation.CarousellNewsTheme
import sg.carousell.news.presentation.model.NewsSortType
import sg.carousell.news.presentation.model.NewsUiEvent
import sg.carousell.news.presentation.model.NewsUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsSortDialog(
    uiState: NewsUiState,
    modifier: Modifier = Modifier,
    onEvent: (NewsUiEvent) -> Unit = {}
) {
    AnimatedVisibility(visible = uiState.isShowSortDialog, modifier) {
        BasicAlertDialog(onDismissRequest = { onEvent(NewsUiEvent.OnSortOptionDismiss) }) {
            Surface(shape = RoundedCornerShape(16.dp), color = CarousellNewsTheme.colors.surface) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Sort Option",
                        style = CarousellNewsTheme.type.title,
                        color = CarousellNewsTheme.colors.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    NewsSortOption(
                        uiState = uiState,
                        text = "Recent",
                        type = NewsSortType.RECENT,
                        onEvent = onEvent,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    NewsSortOption(
                        uiState = uiState,
                        text = "Popular",
                        type = NewsSortType.POPULAR,
                        onEvent = onEvent
                    )
                }
            }
        }
    }
}

@Composable
fun NewsSortOption(
    uiState: NewsUiState,
    text: String,
    type: NewsSortType,
    modifier: Modifier = Modifier,
    onEvent: (NewsUiEvent) -> Unit = {}
) {
    val isSelected = remember(uiState.sortType, type) {
        uiState.sortType == type
    }
    val onClick = remember {
        {
            onEvent(NewsUiEvent.OnSortOptionSelect(type))
            onEvent(NewsUiEvent.OnSortOptionDismiss)
        }
    }
    val containerColorTarget = if (isSelected) {
        CarousellNewsTheme.colors.primary
    } else {
        CarousellNewsTheme.colors.surface
    }
    val textColorTarget = if (isSelected) {
        CarousellNewsTheme.colors.onPrimary
    } else {
        CarousellNewsTheme.colors.textPrimary
    }
    val colorContainer by animateColorAsState(
        targetValue = containerColorTarget,
        label = "Container Color Change"
    )
    val colorText by animateColorAsState(
        targetValue = textColorTarget,
        label = "Text Color Change"
    )
    Surface(
        modifier.clickable { onClick() },
        shape = RoundedCornerShape(32.dp),
        color = colorContainer,
        border = if (isSelected) null else BorderStroke(
            1.dp,
            CarousellNewsTheme.colors.textPrimary
        ),
    ) {
        Text(
            text = text,
            style = CarousellNewsTheme.type.desc,
            textAlign = TextAlign.Center,
            color = colorText,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        )
    }
}
