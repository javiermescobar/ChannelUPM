package models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class NewsItem(
    val NewsItemId: Int,
    val Title: String,
    val Description: String,
    val SendDate: LocalDate,
    val UserId: Int,
    val CategoryId: Int
) {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun getNewsItem(title: String, description: String): NewsItem{
            return NewsItem(
                NewsItemId = -1,
                Title = title,
                Description = description,
                SendDate = LocalDate.of(2023,3,6),
                UserId = -1,
                CategoryId = -1
            )
        }
    }
}