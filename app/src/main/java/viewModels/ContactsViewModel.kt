package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import models.User
import repositories.ContactsRepository
import utils.AppState
import utils.Constants

class ContactsViewModel(
    private val contactsRepository: ContactsRepository,
    private val baseViewModel: BaseViewModel
): ViewModel() {

    val mutableContacts: MutableLiveData<List<User>> = MutableLiveData()

    fun getContacts(userId: Int) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = contactsRepository.getContacts(userId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                mutableContacts.postValue(response.body())
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }
}