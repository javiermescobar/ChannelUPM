package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import models.User
import repositories.LoginRepository
import utils.AppState
import utils.Constants

class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val baseViewModel: BaseViewModel
): ViewModel() {

    val currentUser: MutableLiveData<User> = MutableLiveData()

    fun loginUser(mail: String, password: String) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = loginRepository.LoginUser(mail, password)
            if(response.code() == Constants.ACCEPTED_CODE)  {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                response.body()?.let {
                    currentUser.postValue(it)
                }
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
                currentUser.postValue(User.emptyUser())
            }
        }
    }
}