package sg.carousell.news.core.presentation.utils

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job

interface CustomJob {
    fun set(newJob: Job?)
    fun cancel(cancellationException: CancellationException? = null)
    fun isActive(): Boolean
}

/**
 * Behave like serialDisposable, will cancel previous job when assigned new job by calling serialJob set newJob
 * This class can be used to replace usual job cancellation to avoid double job like:
 *
 * ```
 * val job: Job? = null
 *
 * fun startSomeJob() {
 *  job?.cancel
 *  job = scope.launch {
 *      ...
 *  }
 * }
 * ```
 *
 * Can be replaced with:
 *
 * ```
 * val serialJob = SerialJob()
 *
 * fun startSomeJob() {
 *  serialJob set scope.launch {
 *      ...
 *  }
 * }
 * ```
 */
class SerialJob : CustomJob {
    private var job: Job? = null

    override fun set(newJob: Job?) {
        job?.cancel()
        job = newJob
    }

    override fun cancel(cancellationException: CancellationException?) {
        job?.cancel(cancellationException)
    }

    override fun isActive(): Boolean {
        return job?.isActive == true
    }
}
