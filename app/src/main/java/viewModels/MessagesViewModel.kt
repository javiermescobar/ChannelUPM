package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import models.*
import repositories.LoginRepository
import repositories.MessagesRepository
import utils.AppState
import utils.Constants

class MessagesViewModel(
    private val messagesRepository: MessagesRepository,
    private val loginRepository: LoginRepository,
    private val baseViewModel: BaseViewModel
): ViewModel() {

    val mutableMessages: MutableLiveData<List<PrivateMessage>> = MutableLiveData()
    val mutableMessageSent: MutableLiveData<Boolean> = MutableLiveData()
    val mutableGroups: MutableLiveData<List<GroupChat>> = MutableLiveData()
    val mutableGroupMessages: MutableLiveData<List<GroupMessage>> = MutableLiveData()
    val mutableGroupParticipants: MutableLiveData<List<User>> = MutableLiveData()
    val mutableGroupParticipantsId: MutableLiveData<List<UserInGroup>> = MutableLiveData()
    val mutableUserInGroup: MutableLiveData<Boolean> = MutableLiveData()
    val mutableCurrentGroup: MutableLiveData<GroupChat> = MutableLiveData()

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

    fun createGroupChat(groupName: String, description: String, avatar: String) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = messagesRepository.createGroupChat(groupName, description, avatar)
            if(response.code() == Constants.ACCEPTED_CODE) {
                response.body()?.let {
                    val userAdded = messagesRepository.addUserGroup(it, Constants.currentUser.UserId, 1)
                    if(userAdded.code() == Constants.ACCEPTED_CODE) {
                        baseViewModel.appState.postValue(AppState.SUCCESS)
                    } else {
                        baseViewModel.appState.postValue(AppState.ERROR)
                    }
                }
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

    fun getGroupById(groupChatId: Int) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = messagesRepository.getGroupById(groupChatId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                mutableCurrentGroup.postValue(response.body())
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun addUserGroup(groupChatId: Int, contactId: Int, admin: Int) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = messagesRepository.addUserGroup(groupChatId, contactId, admin)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }


    fun getGroupParticipants(groupChatId: Int) {
        viewModelScope.launch {
            val response = messagesRepository.getGroupParticipants(groupChatId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                mutableGroupParticipantsId.postValue(response.body())
                mutableGroupParticipantsId.value?.let { usersId ->
                    val users = mutableListOf<User>()
                    usersId.forEach { userGroup->
                        val user = loginRepository.getUserById(userGroup.UserId)
                        if(user.code() == Constants.ACCEPTED_CODE) {
                            user.body()?.let {
                                users.add(it)
                            }
                        } else {
                            baseViewModel.appState.postValue(AppState.ERROR)
                        }
                    }
                    mutableGroupParticipants.postValue(users)
                    baseViewModel.appState.postValue(AppState.SUCCESS)
                }
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun isUserInGroup(groupChatId: Int, contactId: Int) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = messagesRepository.isUserInGroup(groupChatId, contactId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                mutableUserInGroup.postValue(response.body() != 0)
                baseViewModel.appState.postValue(AppState.SUCCESS)
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