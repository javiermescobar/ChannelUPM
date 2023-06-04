package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import models.User
import models.UserConfiguration
import repositories.RegisterRepository
import utils.AppState
import utils.Constants

class RegisterViewModel(
    private val registerRepository: RegisterRepository,
    private val baseViewModel: BaseViewModel
): ViewModel(){

    val mutableUserRegistered: MutableLiveData<Int> = MutableLiveData()
    val mutableRegisteredUser: MutableLiveData<User> = MutableLiveData()
    val mutableConfigurationCreated: MutableLiveData<Boolean> = MutableLiveData()
    val mutableConfigurationUpdated: MutableLiveData<Boolean> = MutableLiveData()
    val mutableCreatedConfiguration: MutableLiveData<UserConfiguration> = MutableLiveData()
    val mutableUserInformationUpdated: MutableLiveData<Boolean> = MutableLiveData()

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
                mutableRegisteredUser.postValue(response.body())
                baseViewModel.appState.postValue(AppState.SUCCESS)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun createUserConfiguration(theme: Int, userId: Int) {
        baseViewModel.appState.postValue(AppState.LOADING)
        mutableConfigurationCreated.postValue(false)
        viewModelScope.launch {
            val response = registerRepository.createUserConfiguration(theme, userId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                mutableConfigurationCreated.postValue(true)
                baseViewModel.appState.postValue(AppState.SUCCESS)
            } else {
                mutableConfigurationCreated.postValue(false)
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun getUserConfigurationById(userId: Int) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = registerRepository.getUserConfigurationById(userId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                mutableCreatedConfiguration.postValue(response.body())
                baseViewModel.appState.postValue(AppState.SUCCESS)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun updateUserConfiguration(theme: Int, notifications: Int, configurationId: Int) {
        baseViewModel.appState.postValue(AppState.LOADING)
        mutableConfigurationUpdated.postValue(false)
        viewModelScope.launch {
            val response = registerRepository.updateUserConfiguration(theme, notifications, configurationId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                getUserConfigurationById(Constants.currentUser.UserId)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun updateUserInformation(name: String, description: String, avatarImage: String) {
        baseViewModel.appState.postValue(AppState.LOADING)
        mutableUserInformationUpdated.postValue(false)
        viewModelScope.launch {
            val response = registerRepository.updateUserInformation(name, description, avatarImage)
            if(response.code() == Constants.ACCEPTED_CODE) {
                mutableUserInformationUpdated.postValue(true)
                baseViewModel.appState.postValue(AppState.SUCCESS)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }
}