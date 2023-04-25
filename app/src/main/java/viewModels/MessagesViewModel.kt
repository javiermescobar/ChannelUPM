package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import models.GroupChat
import models.GroupMessage
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
    val mutableGroups: MutableLiveData<List<GroupChat>> = MutableLiveData()
    val mutableGroupMessages: MutableLiveData<List<GroupMessage>> = MutableLiveData()

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

    fun getUserGropus() {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = messagesRepository.getUserGroups()
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                mutableGroups.postValue(response.body())
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun getGroupMessages(groupChatId: Int) {
        viewModelScope.launch{
            val response = messagesRepository.getGroupMessages(groupChatId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                mutableGroupMessages.postValue(response.body())
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun sendGroupMessage(groupChatId: Int, text: String) {
        viewModelScope.launch {
            val response = messagesRepository.sendGroupMessage(groupChatId, text)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                mutableMessageSent.postValue(true)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }
}