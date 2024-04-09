package models

data class PrivateMessage(
    val MessageId: Int,
    val Text: String,
    val SendDate: String,
    val SenderId: Int,
    val ReceiverId: Int
)