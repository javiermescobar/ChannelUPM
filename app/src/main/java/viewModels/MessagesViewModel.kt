package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import models.PrivateMessage
import repositories.MessagesRepository
import utils.AppState
import utils.Constants

class MessagesViewModel(
    private val messagesRepository: MessagesRepository,
    private val baseViewModel: BaseViewModel
): ViewModel() {

    val mutableMessages: MutableLiveData<List<PrivateMessage>> = MutableLiveData()
    val mutableMessageSent: MutableLiveData<Boolean> = MutableLiveData()

    fun getPrivateMessages(contactId: Int) {
        viewModelScope.launch {
            val response = messagesRepository.getPrivateMessage(contactId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                mutableMessages.postValue(response.body())
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun sendPrivateMessage(text: String, contactId: Int) {
        mutableMessageSent.postValue(false)
        viewModelScope.launch {
            val response = messagesRepository.sendPrivateMessage(text, contactId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                mutableMessageSent.postValue(true)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }
}