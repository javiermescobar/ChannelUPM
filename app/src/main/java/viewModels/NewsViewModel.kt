package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import models.InteractiveCategory
import models.NewsItem
import repositories.NewsRepository
import utils.AppState
import utils.Constants
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NewsViewModel(
    private val newsRepository: NewsRepository,
    private val baseViewModel: BaseViewModel
): ViewModel() {

    var mutableNews: MutableLiveData<List<NewsItem>> = MutableLiveData()
    var mutableInterestsRemoved: MutableLiveData<Boolean> = MutableLiveData()
    var mutableUserInterests: MutableLiveData<List<Int>> = MutableLiveData()
    var mutableUserInterestsUpdated: MutableLiveData<Boolean> = MutableLiveData()

    fun getNews(userId: Int) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = newsRepository.getNews(userId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                mutableNews.postValue(response.body())
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun addNew(userId: Int, title: String, description: String, category: InteractiveCategory) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val formatter = DateTimeFormatter.ISO_DATE
            val response = newsRepository.addNewsItem(userId, title,
                LocalDateTime.now().format(formatter), description, category)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun editNew(newId: Int, title: String, description: String, categoryId: Int) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = newsRepository.editNewsItem(newId, title, description, categoryId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun getUserInterests() {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch{
            val response = newsRepository.getUserInterests()
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                mutableUserInterests.postValue(response.body())
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun removeUserInterests() {
        baseViewModel.appState.postValue(AppState.LOADING)
        mutableInterestsRemoved.postValue(false)
        viewModelScope.launch {
            val response = newsRepository.removeUserInterests()
            if(response.code() == Constants.ACCEPTED_CODE) {
                mutableInterestsRemoved.postValue(true)
                baseViewModel.appState.postValue(AppState.SUCCESS)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun addUserInterests(categoriesId: List<Int>) {
        baseViewModel.appState.postValue(AppState.LOADING)
        mutableUserInterestsUpdated.postValue(false)
        viewModelScope.launch {
            val response = newsRepository.addUserInterests(categoriesId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                mutableUserInterestsUpdated.postValue(true)
                baseViewModel.appState.postValue(AppState.SUCCESS)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }
}