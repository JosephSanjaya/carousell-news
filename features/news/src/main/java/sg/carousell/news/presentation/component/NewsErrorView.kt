package sg.carousell.news.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sg.carousell.news.core.presentation.CarousellNewsTheme

@Composable
fun NewsErrorView(
    message: String,
    modifier: Modifier = Modifier,
    onTryAgain: () -> Unit = {}
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                Icons.Filled.Error,
                contentDescription = "Error View Icon",
                colorFilter = ColorFilter.tint(CarousellNewsTheme.colors.primary),
                modifier = Modifier
                    .size(75.dp)
                    .padding(bottom = 16.dp)
            )
            Text(
                text = "Something went wrong",
                style = CarousellNewsTheme.type.title,
                color = CarousellNewsTheme.colors.textPrimary,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
            Text(
                text = message,
                style = CarousellNewsTheme.type.caption,
                color = CarousellNewsTheme.colors.textSecondary,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = onTryAgain,
                shape = RoundedCornerShape(32.dp),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = CarousellNewsTheme.colors.primary,
                    contentColor = CarousellNewsTheme.colors.onPrimary
                )
            ) {
                Text(
                    text = "Try Again",
                    style = CarousellNewsTheme.type.desc,
                    color = CarousellNewsTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
@Preview
private fun NewsErrorViewPreview() {
    CarousellNewsTheme {
        NewsErrorView(message = "Network Error")
    }
}
