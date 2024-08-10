package sg.carousell.news.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.NonRestartableComposable
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
@NonRestartableComposable
fun <T> UiEventEffect(
    event: Flow<T>,
    effect: suspend (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val collectorJob = SerialJob()
        lifecycleOwner.lifecycleScope.launch {
            event.flowWithLifecycle(lifecycleOwner.lifecycle).collect {
                effect(it)
            }
        }.also(collectorJob::set)
        onDispose {
            collectorJob.cancel()
        }
    }
}
