package models

data class UserInGroup(
    val UserId: Int,
    val Administrator: Int,
    val Name: String,
    val AvatarImage: String
)