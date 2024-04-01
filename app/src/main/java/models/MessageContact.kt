package models

class MessageContact(
    val UserId: Int,
    val AvatarImage: String,
    val Name: String,
    var LastMessage: String
) {}