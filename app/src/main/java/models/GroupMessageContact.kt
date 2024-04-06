package models

data class GroupMessageContact (
    val UserId: Int,
    val AvatarImage: String,
    val GroupName: String,
    val UserName: String,
    var LastMessage: String
)