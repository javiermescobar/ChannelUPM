package models

data class GroupMessage(
    val GroupMessageId: Int,
    val Text: String,
    val SendDate: String,
    val GroupChatId: Int,
    val SenderId: Int,
    val AvatarImage: String,
    val SenderName: String
)