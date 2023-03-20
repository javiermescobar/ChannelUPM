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
            val response = loginRepository.loginUser(mail, password)
            if(response.code() == Constants.ACCEPTED_CODE)  {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                if(response.body() != 0) {
                    response.body()?.let {
                        getUserById(it)
                    }
                }
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
                currentUser.postValue(User.emptyUser())
            }
        }
    }

    private fun getUserById(userId: Int) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = loginRepository.getUserById(userId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                currentUser.postValue(response.body())
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }
}