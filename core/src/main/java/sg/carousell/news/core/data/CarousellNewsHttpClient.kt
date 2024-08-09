package sg.carousell.news.core.data

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dagger.hilt.android.qualifiers.ApplicationContext
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton
import kotlinx.serialization.json.Json
import javax.inject.Inject

@Singleton
class CarousellNewsHttpClient @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json
) {

    val client = Ktorfit.Builder()
        .baseUrl(BASE_URL)
        .httpClient(constructHttpClient())
        .build()

    private fun constructHttpClient() = HttpClient(constructOkHttpClient()) {
        install(ContentNegotiation) {
            json(json)
        }
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
            header(HttpHeaders.Accept, ContentType.Application.Json)
        }
    }

    private fun constructOkHttpClient() = OkHttp.create {
        addInterceptor(constructChuckerInterceptor())
    }

    private fun constructChuckerInterceptor() = ChuckerInterceptor.Builder(context)
        .collector(constructChuckerCollector()).maxContentLength(CHUCKER_MAX_LENGTH)
        .alwaysReadResponseBody(true)
        .createShortcut(true)
        .build()

    private fun constructChuckerCollector() = ChuckerCollector(
        context = context,
        showNotification = true,
        retentionPeriod = RetentionManager.Period.ONE_HOUR
    )

    companion object {
        private const val BASE_URL =
            "https://storage.googleapis.com/carousell-interview-assets/android/"
        private const val CHUCKER_MAX_LENGTH = 250_000L
    }
}
