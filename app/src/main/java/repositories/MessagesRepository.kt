package repositories

import apis.RetrofitInstance
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
}