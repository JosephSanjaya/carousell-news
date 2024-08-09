package sg.carousell.news.core.presentation.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
@Immutable
data class CarousellNewsColor(
    val primary: Color = Color.Unspecified,
    val onPrimary: Color = Color.Unspecified,
    val surface: Color = Color.Unspecified,
    val textPrimary: Color = Color.Unspecified,
    val textSecondary: Color = Color.Unspecified,
    val background: Color = Color.Unspecified,
)
