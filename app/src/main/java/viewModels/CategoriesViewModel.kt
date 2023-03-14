package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import models.Category
import repositories.CategoriesRepository
import utils.AppState
import utils.Constants

class CategoriesViewModel(
    private val categoriesRepository: CategoriesRepository,
    private val baseViewModel: BaseViewModel
): ViewModel() {

    val categories: MutableLiveData<List<Category>> = MutableLiveData()

    suspend fun getCategories() {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = categoriesRepository.getCategories()
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                categories.postValue(response.body())
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }
}