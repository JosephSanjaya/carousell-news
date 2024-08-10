package sg.carousell.news.domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import sg.carousell.news.data.model.NewsData

@Keep
@Serializable
data class NewsDomain(
    val bannerUrl: String,
    val description: String,
    val id: String,
    val rank: Int,
    val timeCreated: Long,
    val title: String
) {
    constructor(data: NewsData?): this(
        bannerUrl = data?.bannerUrl.orEmpty(),
        description = data?.description.orEmpty(),
        id = data?.id.orEmpty(),
        rank = data?.rank ?: 0,
        timeCreated = data?.timeCreated ?: 0,
        title = data?.title.orEmpty()
    )
}
