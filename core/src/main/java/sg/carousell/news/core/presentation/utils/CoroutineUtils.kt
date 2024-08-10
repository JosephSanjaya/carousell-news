package sg.carousell.news.core.presentation.utils

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.cancellation.CancellationException

fun CoroutineScope.safeLaunch(
    context: CoroutineContext = coroutineContext,
    onError: suspend (Throwable) -> Unit = {},
    onCancellation: suspend (Throwable) -> Unit = {},
    block: suspend CoroutineScope.() -> Unit
): Job {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        this.launch(context) {
            onError(throwable)
        }
    }
    return launch(context + exceptionHandler) {
        runCatching {
            block()
        }.onFailure {
            if (it is CancellationException) {
                onCancellation(it)
                throw it
            } else {
                onError(it)
            }
        }
    }
}
