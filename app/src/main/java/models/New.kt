package models

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class New(
    val NewId: Int,
    val Title: String,
    val Description: String,
    val SendDate: LocalDate,
    val UserId: Int,
    val CategoryId: Int
) {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun getNew(title: String, description: String): New{
            return New(
                NewId = -1,
                Title = title,
                Description = description,
                SendDate = LocalDate.of(2023,3,6),
                UserId = -1,
                CategoryId = -1
            )
        }
    }
}