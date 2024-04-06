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
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap

class MessagesViewModel(
    private val messagesRepository: MessagesRepository,
    private val baseViewModel: BaseViewModel
): ViewModel() {

    val mutableMessages: MutableLiveData<List<PrivateMessage>> = MutableLiveData()
    val mutableMessageSent: MutableLiveData<Boolean> = MutableLiveData()
    val mutableGroups: MutableLiveData<List<GroupChat>> = MutableLiveData()
    val mutableGroupMessages: MutableLiveData<List<GroupMessage>> = MutableLiveData()
    val mutableGroupParticipants: MutableLiveData<List<UserInGroup>> = MutableLiveData()
    val mutableUserInGroup: MutableLiveData<Boolean> = MutableLiveData()
    val mutableCurrentGroup: MutableLiveData<GroupChat> = MutableLiveData()
    val mutableEditedUser: MutableLiveData<Int> = MutableLiveData()
    val mutableUserInGroupRemoved: MutableLiveData<Boolean> = MutableLiveData()
    val mutableGroupRemoved: MutableLiveData<Boolean> = MutableLiveData()
    val mutableMessagesContact: MutableLiveData<HashMap<User, LastPrivateMessage>> = MutableLiveData()
    val mutableMessageGroup: MutableLiveData<HashMap<GroupChat, LastGroupMessage>> = MutableLiveData()

    private val messageContactHashMap = HashMap<User, LastPrivateMessage>()
    private val messageGroupHashMap = HashMap<GroupChat, LastGroupMessage>()

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
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val response = messagesRepository.sendPrivateMessage(text,
                LocalDateTime.now().format(formatter).toString(), contactId)
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

    fun getUserGroups() {
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

    fun searchGroups(searchString: String) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = messagesRepository.searchGroup(searchString)
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

    fun updateUserGroup(groupChatId: Int, contactId: Int, admin: Int) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = messagesRepository.updateUserGroup(groupChatId, contactId, admin)
            if(response.code() == Constants.ACCEPTED_CODE) {
                mutableEditedUser.postValue(response.body())
                getGroupParticipants(groupChatId)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun updateGroupChat(groupChatId: Int, groupName: String, description: String, avatarImage: String) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = messagesRepository.updateGroupChat(groupChatId, groupName, description, avatarImage)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun getGroupParticipants(groupChatId: Int) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = messagesRepository.getGroupParticipants(groupChatId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                mutableGroupParticipants.postValue(response.body())
                baseViewModel.appState.postValue(AppState.SUCCESS)
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
        mutableMessageSent.postValue(false)
        viewModelScope.launch {
            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val response = messagesRepository.sendGroupMessage(groupChatId, text,
                LocalDateTime.now().format(formatter).toString())
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                mutableMessageSent.postValue(true)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun removeUserGroup(groupChatId: Int) {
        mutableUserInGroupRemoved.postValue(false)
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = messagesRepository.removeUserGroup(groupChatId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                mutableUserInGroupRemoved.postValue(true)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun removeGroup(groupChatId: Int) {
        mutableGroupRemoved.postValue(false)
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = messagesRepository.removeGroup(groupChatId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                baseViewModel.appState.postValue(AppState.SUCCESS)
                mutableGroupRemoved.postValue(true)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun getLastPrivateMessage(userId: Int, user: User) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = messagesRepository.getLastPrivateMessage(userId, user.UserId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                response.body()?.let {
                    messageContactHashMap.put(user, it)
                }
                mutableMessagesContact.postValue(messageContactHashMap)
                baseViewModel.appState.postValue(AppState.SUCCESS)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }

    fun getLastGroupMessage(group: GroupChat) {
        baseViewModel.appState.postValue(AppState.LOADING)
        viewModelScope.launch {
            val response = messagesRepository.getLastGroupMessage(group.GroupChatId)
            if(response.code() == Constants.ACCEPTED_CODE) {
                response.body()?.let {
                    messageGroupHashMap.put(group, it)
                }
                mutableMessageGroup.postValue(messageGroupHashMap)
                baseViewModel.appState.postValue(AppState.SUCCESS)
            } else {
                baseViewModel.appState.postValue(AppState.ERROR)
            }
        }
    }
}