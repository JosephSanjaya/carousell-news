package sg.carousell.news.core.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle

@Immutable
@Stable
data class CarousellNewsType(
    val header: TextStyle = TextStyle.Default,
    val title: TextStyle = TextStyle.Default,
    val desc: TextStyle = TextStyle.Default,
    val caption: TextStyle = TextStyle.Default,
)
