package models

import java.util.*

data class New(
    private val NewId: Int,
    private val Title: String,
    private val Description: String,
    private val SendDate: Date,
    private val UserId: Int,
    private val CategoryId: Int
) {
    companion object {
        fun getNew(title: String, description: String): New{
            return New(
                NewId = -1,
                Title = title,
                Description = description,
                SendDate = Date(2023,3,5),
                UserId = -1,
                CategoryId = -1
            )
        }
    }
}