package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import models.User
import repositories.LoginRepository
import retrofit2.Response
import timber.log.Timber
import utils.Constants
import java.lang.Exception

class LoginViewModel(
    val loginRepository: LoginRepository
): ViewModel() {

    val currentUser: MutableLiveData<User> = MutableLiveData()

    fun loginUser(mail: String, password: String) {
        viewModelScope.launch {
            val response = loginRepository.LoginUser(mail, password)
            if(response.code() == Constants.ACCEPTED_CODE)  {
                response.body()?.let {
                    currentUser.postValue(it)
                }
            } else {
                currentUser.postValue(User.emptyUser())
            }
        }
    }
}