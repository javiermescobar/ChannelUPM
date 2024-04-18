package models

class PrivateMessageContact(
    val UserId: Int,
    val SenderId: Int,
    val AvatarImage: String,
    val Name: String,
    var LastMessage: String
)