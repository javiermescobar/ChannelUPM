package models

data class PrivateMessage(
    val MessageId: Int,
    val Text: String,
    val SendDate: String,
    val SenderId: Int,
    val ReceiverId: Int
) {
    companion object {
        fun newMessage(date: String): PrivateMessage {
            return PrivateMessage(
                1,
                date,
                date,
                1,
                1
            )
        }
    }
}