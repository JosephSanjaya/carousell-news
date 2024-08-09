package sg.carousell.news.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep

@Keep
@Serializable
data class NewsData(
    @SerialName("banner_url")
    val bannerUrl: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("rank")
    val rank: Int?,
    @SerialName("time_created")
    val timeCreated: Int?,
    @SerialName("title")
    val title: String?
)
