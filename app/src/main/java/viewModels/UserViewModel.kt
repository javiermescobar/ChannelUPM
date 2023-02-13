package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import models.User
import repositories.UserRepository
import retrofit2.Response

class UserViewModel(
    val userRepository: UserRepository
): ViewModel() {

    var user: MutableLiveData<User> = MutableLiveData()

    fun getUser() {
        viewModelScope.launch {
            val response = userRepository.getUser()
            if(response.isSuccessful) {
                user.value = response.body()
            }
        }
    }
}