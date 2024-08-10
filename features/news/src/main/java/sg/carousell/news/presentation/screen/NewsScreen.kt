package sg.carousell.news.presentation.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import sg.carousell.news.R
import sg.carousell.news.core.presentation.CarousellNewsTheme
import sg.carousell.news.core.presentation.utils.UiEventEffect
import sg.carousell.news.presentation.component.NewsCard
import sg.carousell.news.presentation.component.NewsErrorView
import sg.carousell.news.presentation.component.NewsLoadingView
import sg.carousell.news.presentation.component.NewsSortDialog
import sg.carousell.news.presentation.model.NewsUiEvent
import sg.carousell.news.presentation.model.NewsUiState

@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    UiEventEffect(event = viewModel.uiEvent) { event ->
        when (event) {
            is NewsUiEvent.OnNewsClick -> Toast.makeText(
                context,
                event.news.title,
                Toast.LENGTH_SHORT
            ).show()

            else -> {
                /** Do Nothing **/
            }
        }
    }
    val onEvent = remember<(NewsUiEvent) -> Unit> {
        { viewModel.onEvent(it) }
    }
    LaunchedEffect(key1 = Unit) {
        onEvent(NewsUiEvent.OnFetchNews)
    }
    CarousellNewsTheme {
        NewsScreen(uiState = uiState, modifier = modifier, onEvent = onEvent)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    uiState: NewsUiState,
    modifier: Modifier = Modifier,
    onEvent: (NewsUiEvent) -> Unit = {}
) {
    Scaffold(modifier = modifier.background(CarousellNewsTheme.colors.background), topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors()
                .copy(containerColor = CarousellNewsTheme.colors.primary),
            title = {
                Text(
                    text = stringResource(R.string.app_name),
                    style = CarousellNewsTheme.type.title,
                    color = CarousellNewsTheme.colors.onPrimary
                )
            },
            actions = {
                IconButton(onClick = { onEvent(NewsUiEvent.ShowSortOption) }) {
                    Image(
                        Icons.Filled.MoreHoriz,
                        colorFilter = ColorFilter.tint(CarousellNewsTheme.colors.onPrimary),
                        contentDescription = "Menu"
                    )
                }
            })
    }) {
        AnimatedContent(
            targetState = uiState.error to uiState.isLoading,
            label = "News Content"
        ) { (error, isLoading) ->
            when {
                error != null -> {
                    NewsErrorView(
                        message = error,
                        onTryAgain = { onEvent(NewsUiEvent.OnFetchNews) })
                }

                isLoading -> {
                    NewsLoadingView()
                }

                else -> {
                    LazyColumn(modifier = Modifier.padding(it)) {
                        items(uiState.data) { news ->
                            if (uiState.data.first() == news) Spacer(modifier = Modifier.size(8.dp))
                            NewsCard(
                                news = news,
                                modifier = Modifier.padding(horizontal = 8.dp),
                                onEvent = onEvent
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                        }
                    }
                }
            }
        }
    }
    NewsSortDialog(uiState = uiState, onEvent = onEvent)
}
