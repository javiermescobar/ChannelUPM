package models

data class NewsItem(
    val NewsItemId: Int,
    val Title: String,
    val Description: String,
    val SendDate: String,
    val UserId: Int,
    val CategoryId: Int,
    val CategoryName: String
)