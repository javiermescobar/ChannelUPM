package models

data class User(
    val UserId: Int,
    val Name: String,
    val Mail: String,
    val UserPassword: String,
    val Description: String,
    val AvatarImage: String,
    val Administrator: Int
)