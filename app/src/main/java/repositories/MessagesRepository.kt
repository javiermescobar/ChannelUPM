package repositories

import apis.RetrofitInstance
import models.GroupChat
import models.GroupMessage
import models.LastGroupMessage
import models.LastPrivateMessage
import models.PrivateMessage
import models.UserInGroup
import retrofit2.Response
import utils.Constants

class MessagesRepository {

    suspend fun getPrivateMessage(contactId: Int): Response<List<PrivateMessage>> {
        return RetrofitInstance.messagesApi.getPrivateMessages(
            "https://sywizmgkb3wfijim5pt4sbeqem0eofkc.lambda-url.eu-west-2.on.aws/",
            Constants.currentUser.UserId, contactId)
    }

    suspend fun sendPrivateMessage(text: String, date: String, contactId: Int): Response<Int> {
        return RetrofitInstance.messagesApi.sendPrivateMessage(
            "https://wdw2rt3wiocop2l4ayd2uymgmu0bromi.lambda-url.eu-west-2.on.aws/",
            text, date, Constants.currentUser.UserId, contactId)
    }

    suspend fun createGroupChat(groupName: String, description: String, avatar: String): Response<Int> {
        return RetrofitInstance.messagesApi.createGroupChat(
            "https://2a3ytursjq7y2citk2bmbtafiy0bijri.lambda-url.eu-west-2.on.aws/",
            groupName, avatar, description)
    }

    suspend fun addUserGroup(groupChatId: Int, contactId: Int, admin: Int): Response<Int> {
        return RetrofitInstance.messagesApi.addUserGroup(
            "https://fjatrqlhjfh7kn4ienbqw2hcpa0qbjwy.lambda-url.eu-west-2.on.aws/",
            admin, groupChatId, contactId)
    }

    suspend fun updateUserGroup(groupChatId: Int, contactId: Int, admin: Int): Response<Int> {
        return RetrofitInstance.messagesApi.updateUserGroup(
            "https://jv3mp7loyts6fvsof43rgqctmm0pslif.lambda-url.eu-west-2.on.aws/",
            admin, groupChatId, contactId)
    }

    suspend fun updateGroupChat(groupChatId: Int, groupName: String, description: String, avatarImage: String): Response<Int> {
        return RetrofitInstance.messagesApi.updateGroupChat(
            "https://izxuic42cwktrgf3lwuh5uiuny0lkzew.lambda-url.eu-west-2.on.aws/",
            groupChatId, groupName, description, avatarImage)
    }

    suspend fun getGroupParticipants(groupChatId: Int): Response<List<UserInGroup>> {
        return RetrofitInstance.messagesApi.getGroupParticipants(
            "https://gmnhdbbiwpd7tsfuuji6xaum5e0nlewk.lambda-url.eu-west-2.on.aws/",
            groupChatId
        )
    }

    suspend fun isUserInGroup(groupChatId: Int, contactId: Int): Response<Int> {
        return RetrofitInstance.messagesApi.isUserInGroup(
            "https://engj3imwghpwstbd36aowntjcq0wgjlr.lambda-url.eu-west-2.on.aws/",
            groupChatId, contactId)
    }

    suspend fun getUserGroups(): Response<List<GroupChat>> {
        return RetrofitInstance.messagesApi.getUserGroups(
            "https://wrwozcxnzgo7b7venaahrck6ai0zoxoq.lambda-url.eu-west-2.on.aws/",
        Constants.currentUser.UserId)
    }

    suspend fun searchGroup(searchString: String): Response<List<GroupChat>> {
        return RetrofitInstance.messagesApi.searchGroup(
            "https://x2wsbfsrrl37mdxudn7z33c64m0tngud.lambda-url.eu-west-2.on.aws/",
            Constants.currentUser.UserId, searchString
        )
    }

    suspend fun getGroupById(groupChatId: Int): Response<GroupChat> {
        return RetrofitInstance.messagesApi.getGroupById(
            "https://mud2i3bnfzrdwyhjb43itoftum0blwyz.lambda-url.eu-west-2.on.aws/",
            groupChatId)
    }

    suspend fun getGroupMessages(groupMessageId: Int): Response<List<GroupMessage>> {
        return RetrofitInstance.messagesApi.getChatGroupMessages(
            "https://mzwgcdezffapjvpw6lubu76tae0atapq.lambda-url.eu-west-2.on.aws/",
            groupMessageId)
    }

    suspend fun sendGroupMessage(groupChatId: Int ,text: String, date: String): Response<Int> {
        return RetrofitInstance.messagesApi.sendGroupMessage(
            "https://7h7mcfc27atjbon44bfd744r2y0yguhg.lambda-url.eu-west-2.on.aws/",
            groupChatId, text, date, Constants.currentUser.UserId,
            Constants.currentUser.AvatarImage, Constants.currentUser.Name)
    }

    suspend fun removeUserGroup(groupChatId: Int): Response<Int> {
        return RetrofitInstance.messagesApi.removeUserGroup(
            "https://yq3dsltaihcwfydbdloogg6x2u0uppno.lambda-url.eu-west-2.on.aws/",
        Constants.currentUser.UserId, groupChatId)
    }

    suspend fun removeGroup(groupChatId: Int): Response<Int> {
        return RetrofitInstance.messagesApi.removeGroup(
            "https://enlafdklwfj3f5jxf6svua4mfq0jsaet.lambda-url.eu-west-2.on.aws/",
            groupChatId)
    }

    suspend fun getLastPrivateMessage(userId: Int, contactId: Int): Response<LastPrivateMessage> {
        return RetrofitInstance.messagesApi.getLastPrivateMessage(
            "https://6xljrehjwvav4rdvyijfrji37e0vsuki.lambda-url.eu-west-2.on.aws/",
            userId, contactId)
    }

    suspend fun getLastGroupMessage(groupChatId: Int): Response<LastGroupMessage> {
        return RetrofitInstance.messagesApi.getLastGroupMessage(
            "https://34ug6jdygvuwixs6m2himg7w3m0sjwir.lambda-url.eu-west-2.on.aws/",
            groupChatId)
    }
}