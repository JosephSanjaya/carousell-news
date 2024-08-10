package sg.carousell.news.domain.usecase

import sg.carousell.news.data.NewsRepo
import sg.carousell.news.domain.model.NewsDomain
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchNewsUseCase @Inject constructor(
    private val repo: NewsRepo
) {
    suspend operator fun invoke(): List<NewsDomain> {
        return repo.getNews().map(::NewsDomain)
    }
}
