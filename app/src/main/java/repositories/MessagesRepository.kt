package repositories

import apis.RetrofitInstance
import models.GroupChat
import models.GroupMessage
import models.PrivateMessage
import models.UserInGroup
import retrofit2.Response
import utils.Constants

class MessagesRepository {

    suspend fun getPrivateMessage(contactId: Int): Response<List<PrivateMessage>> {
        return RetrofitInstance.messagesApi.getPrivateMessages(
            "https://52mpwxvminpwdebzthxm2w6ssi0gfhof.lambda-url.eu-west-2.on.aws/",
            Constants.currentUser.UserId, contactId)
    }

    suspend fun sendPrivateMessage(text: String, date: String, contactId: Int): Response<Int> {
        return RetrofitInstance.messagesApi.sendPrivateMessage(
            "https://qmhgbrdozzujanbbs2lyqxyole0qrbqz.lambda-url.eu-west-2.on.aws/",
            text, date, Constants.currentUser.UserId, contactId)
    }

    suspend fun createGroupChat(groupName: String, description: String, avatar: String): Response<Int> {
        return RetrofitInstance.messagesApi.createGroupChat(
            "https://uaknky72ykynnjwsdexjftgj7i0rlqax.lambda-url.eu-west-2.on.aws/",
            groupName, avatar, description)
    }

    suspend fun addUserGroup(groupChatId: Int, contactId: Int, admin: Int): Response<Int> {
        return RetrofitInstance.messagesApi.addUserGroup(
            "https://4do6hqk44by2enkkanmipa4fpy0ahtgt.lambda-url.eu-west-2.on.aws/",
            admin, groupChatId, contactId)
    }

    suspend fun updateUserGroup(groupChatId: Int, contactId: Int, admin: Int): Response<Int> {
        return RetrofitInstance.messagesApi.updateUserGroup(
            "https://ndevktzh2g2eu77uxim6gflbly0cpdwl.lambda-url.eu-west-2.on.aws/",
            admin, groupChatId, contactId)
    }

    suspend fun updateGroupChat(groupChatId: Int, groupName: String, description: String, avatarImage: String): Response<Int> {
        return RetrofitInstance.messagesApi.updateGroupChat(
            "https://abbv4m2ppcvcmop2fmdmgjvtsy0fkbhh.lambda-url.eu-west-2.on.aws/",
            groupChatId, groupName, description, avatarImage)
    }

    suspend fun getGroupParticipants(groupChatId: Int): Response<List<UserInGroup>> {
        return RetrofitInstance.messagesApi.getGroupParticipants(
            "https://ykxmmllimzvnzxrysun7c7rrje0grpqp.lambda-url.eu-west-2.on.aws/",
            groupChatId
        )
    }

    suspend fun isUserInGroup(groupChatId: Int, contactId: Int): Response<Int> {
        return RetrofitInstance.messagesApi.isUserInGroup(
            "https://dzqs4c4jg6dnmthjw34enqkbey0zcizo.lambda-url.eu-west-2.on.aws/",
            groupChatId, contactId)
    }

    suspend fun getUserGroups(): Response<List<GroupChat>> {
        return RetrofitInstance.messagesApi.getUserGroups(
            "https://npzwm3iebvmmf7wnk7izjgop4m0xzeme.lambda-url.eu-west-2.on.aws/",
        Constants.currentUser.UserId)
    }

    suspend fun getGroupById(groupChatId: Int): Response<GroupChat> {
        return RetrofitInstance.messagesApi.getGroupById(
            "https://u7linv3mybq2hasu7aviiug6ta0bsohg.lambda-url.eu-west-2.on.aws/",
            groupChatId)
    }

    suspend fun getGroupMessages(groupMessageId: Int): Response<List<GroupMessage>> {
        return RetrofitInstance.messagesApi.getChatGroupMessages(
            "https://tizmvzftiedrpfno6yezqeldra0jywgn.lambda-url.eu-west-2.on.aws/",
            groupMessageId)
    }

    suspend fun sendGroupMessage(groupChatId: Int ,text: String, date: String): Response<Int> {
        return RetrofitInstance.messagesApi.sendGroupMessage(
            "https://magt755g2oagtnhosohl7jt6ua0kfjif.lambda-url.eu-west-2.on.aws/",
            groupChatId, text, date, Constants.currentUser.UserId,
            Constants.currentUser.AvatarImage, Constants.currentUser.Name)
    }
}