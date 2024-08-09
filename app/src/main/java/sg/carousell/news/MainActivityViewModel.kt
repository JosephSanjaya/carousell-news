package sg.carousell.news

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sg.carousell.news.data.NewsRepo
import sg.carousell.news.data.model.NewsData
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val newsRepo: NewsRepo
) : ViewModel() {

    var news by mutableStateOf<List<NewsData>>(emptyList())
        private set

    fun getNews() = viewModelScope.launch {
        news = newsRepo.getNews()
    }
}