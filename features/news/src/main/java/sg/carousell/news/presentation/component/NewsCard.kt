package sg.carousell.news.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import sg.carousell.news.core.presentation.CarousellNewsTheme
import sg.carousell.news.domain.model.NewsDomain
import sg.carousell.news.presentation.model.NewsUiEvent
import sg.carousell.news.presentation.utils.DateUtils

@Composable
fun NewsCard(
    news: NewsDomain,
    modifier: Modifier = Modifier,
    onEvent: (NewsUiEvent) -> Unit = {}
) {
    val context = LocalContext.current
    val onClick = remember {
        { onEvent(NewsUiEvent.OnNewsClick(news)) }
    }
    Surface(
        modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(news.bannerUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "News Banner",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .padding(bottom = 12.dp)
            )
            val contentHorizontalPadding = remember { 16.dp }
            Text(
                text = news.title,
                style = CarousellNewsTheme.type.title,
                maxLines = 2,
                color = CarousellNewsTheme.colors.textPrimary,
                modifier = Modifier
                    .padding(horizontal = contentHorizontalPadding)
                    .padding(bottom = 4.dp)
            )
            Text(
                text = news.description,
                style = CarousellNewsTheme.type.desc,
                maxLines = 2,
                color = CarousellNewsTheme.colors.textPrimary,
                modifier = Modifier
                    .padding(horizontal = contentHorizontalPadding)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = DateUtils.getRelativeTime(news.timeCreated),
                style = CarousellNewsTheme.type.caption,
                color = CarousellNewsTheme.colors.textSecondary,
                modifier = Modifier
                    .padding(horizontal = contentHorizontalPadding)
                    .padding(bottom = 12.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NewsCardPreview() {
    CarousellNewsTheme {
        NewsCard(
            news = NewsDomain(
                bannerUrl = "https://search.yahoo.com/search?p=veniam",
                description = "neglegentur",
                id = "bibendum",
                rank = 8319,
                timeCreated = 8908,
                title = "qui"
            )
        )
    }
}
