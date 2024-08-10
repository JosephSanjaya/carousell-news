package sg.carousell.news.utils

import sg.carousell.news.data.model.NewsData
import sg.carousell.news.domain.model.NewsDomain

fun mockNewsData() = NewsData(
    bannerUrl = "test",
    description = "test",
    id = "test",
    rank = 1,
    timeCreated = 1,
    title = "test",
)

fun mockNewsDomain(title: String) = NewsDomain(
    bannerUrl = "https://duckduckgo.com/?q=veri",
    description = "tritani",
    id = "te",
    rank = 1,
    timeCreated = 1000L,
    title = title
)

fun mockListNewsDomain() = listOf(
    mockNewsDomain("News 1"),
    mockNewsDomain("News 2")
)
