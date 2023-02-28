package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import repositories.RegisterRepository
import retrofit2.Response
import utils.AppState
import utils.Constants

class RegisterViewModel(
    private val registerRepository: RegisterRepository,
    private val baseViewModel: BaseViewModel
): ViewModel(){

    var mutableUserRegistered: MutableLiveData<Int> = MutableLiveData()

    fun getMailRegistered(mail: String) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = registerRepository.mailRegistered(mail)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                response.body()?.let {
                    mutableUserRegistered.postValue(it)
                }
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
                mutableUserRegistered.postValue(1)
            }
        }
    }

    fun registerUser(name: String, mail: String, password: String) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = registerRepository.registerUser(name, mail, password)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }
}