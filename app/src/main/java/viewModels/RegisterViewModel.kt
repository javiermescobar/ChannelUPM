package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import repositories.RegisterRepository
import retrofit2.Response

class RegisterViewModel(
    val registerRepository: RegisterRepository
): ViewModel() {

    var mutableUserRegistered: MutableLiveData<Response<Int>> = MutableLiveData()

    fun getMailRegistered(mail: String) {
        viewModelScope.launch {
            val response = registerRepository.mailRegistered(mail)
            mutableUserRegistered.value = response
        }
    }

    fun registerUser(name: String, mail: String, password: String) {
        viewModelScope.launch {
            registerRepository.registerUser(name, mail, password)
        }
    }
}