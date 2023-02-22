package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import repositories.RegisterRepository
import retrofit2.Response
import utils.Constants

class RegisterViewModel(
    val registerRepository: RegisterRepository
): ViewModel() {

    var mutableUserRegistered: MutableLiveData<Int> = MutableLiveData()

    fun getMailRegistered(mail: String) {
        viewModelScope.launch {
            val response = registerRepository.mailRegistered(mail)
            if(response.code() == Constants.ACCEPTED_CODE) {
                response.body()?.let {
                    mutableUserRegistered.postValue(it)
                }
            } else {
                mutableUserRegistered.postValue(1)
            }
        }
    }

    fun registerUser(name: String, mail: String, password: String) {
        viewModelScope.launch {
            val response = registerRepository.registerUser(name, mail, password)
            if(response.code() == Constants.ACCEPTED_CODE) {
                //Show user created successfully dialog
            } else {
                //Show error dialog
            }
        }
    }
}