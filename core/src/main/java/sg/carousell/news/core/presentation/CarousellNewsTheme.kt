package sg.carousell.news.core.presentation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import sg.carousell.news.core.presentation.model.CarousellNewsColor
import sg.carousell.news.core.presentation.model.CarousellNewsType

val LocalCarousellNewsColor = staticCompositionLocalOf {
    CarousellNewsColor()
}

val LocalCarousellNewsType = staticCompositionLocalOf {
    CarousellNewsType()
}

data object CarousellNewsTheme {
    val colors: CarousellNewsColor
        @Composable
        @ReadOnlyComposable
        get() = LocalCarousellNewsColor.current

    val type: CarousellNewsType
        @Composable
        @ReadOnlyComposable
        get() = LocalCarousellNewsType.current
}

@Composable
fun CarousellNewsTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalCarousellNewsColor provides CarousellNewsColor(
            primary = Color(0xFFD2232A),
            onPrimary = Color.White,
            surface = Color.White,
            textPrimary = Color(0xFF262629),
            textSecondary = Color(0xFF8F939C),
            background = Color(0xFFEDF0F7),
        ),
        LocalCarousellNewsType provides CarousellNewsType(
            header = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            title = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                letterSpacing = 0.11.sp
            ),
            desc = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = 0.1.sp
            ),
            caption = TextStyle(
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = 0.09.sp
            )
        ),
    ) {
        content()
    }
}
