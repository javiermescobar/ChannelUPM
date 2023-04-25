package repositories

import apis.RetrofitInstance
import models.GroupChat
import models.GroupMessage
import models.PrivateMessage
import retrofit2.Response
import utils.Constants

class MessagesRepository {

    suspend fun getPrivateMessage(contactId: Int): Response<List<PrivateMessage>> {
        return RetrofitInstance.messagesApi.getPrivateMessages(
            "https://52mpwxvminpwdebzthxm2w6ssi0gfhof.lambda-url.eu-west-2.on.aws/",
            Constants.currentUser.UserId, contactId)
    }

    suspend fun sendPrivateMessage(text: String, contactId: Int): Response<Int> {
        return RetrofitInstance.messagesApi.sendPrivateMessage(
            "https://qmhgbrdozzujanbbs2lyqxyole0qrbqz.lambda-url.eu-west-2.on.aws/",
            text, Constants.currentUser.UserId, contactId)
    }

    suspend fun getUserGroups(): Response<List<GroupChat>> {
        return RetrofitInstance.messagesApi.getUserGroups(
            "https://npzwm3iebvmmf7wnk7izjgop4m0xzeme.lambda-url.eu-west-2.on.aws/",
        Constants.currentUser.UserId)
    }

    suspend fun getGroupMessages(groupMessageId: Int): Response<List<GroupMessage>> {
        return RetrofitInstance.messagesApi.getChatGroupMessages(
            "https://tizmvzftiedrpfno6yezqeldra0jywgn.lambda-url.eu-west-2.on.aws/",
            groupMessageId)
    }

    suspend fun sendGroupMessage(groupChatId: Int ,text: String): Response<Int> {
        return RetrofitInstance.messagesApi.sendGroupMessage(
            "https://qmhgbrdozzujanbbs2lyqxyole0qrbqz.lambda-url.eu-west-2.on.aws/",
            groupChatId, text, Constants.currentUser.UserId)
    }
}