package repositories

import apis.RetrofitInstance
import models.InteractiveCategory
import models.NewsItem
import retrofit2.Response
import utils.Constants
import java.time.LocalDate
import java.time.LocalDateTime

class NewsRepository {

    suspend fun getNews(userId: Int): Response<List<NewsItem>> {
        return RetrofitInstance.newsApi.getNews("https://wfu532amhqr4s6xskywzkcwtzy0ugimn.lambda-url.eu-west-2.on.aws/",
        userId = userId)
    }

    suspend fun addNewsItem(userId: Int, title: String, date: String, description: String, category: InteractiveCategory): Response<Int> {
        return RetrofitInstance.newsApi.addNewsItem("https://5ezpzqmafccz6nmzejsmwozr2m0nbdto.lambda-url.eu-west-2.on.aws/",
            userId = userId,
            title = title,
            date = date,
            description = description,
            categoryId = category.categoryId,
            categoryName = category.categoryTitle)
    }

    suspend fun editNewsItem(newId: Int, title: String, description: String, categoryId: Int): Response<Int> {
        return RetrofitInstance.newsApi.editNewsItem("https://kla55kqkpfixeylzoxiuwje6bu0wcjsm.lambda-url.eu-west-2.on.aws/",
        newId = newId,
        title = title,
        description = description,
        categoryId = categoryId)
    }

    suspend fun getUserInterests(): Response<List<Int>> {
        return RetrofitInstance.newsApi.getUserInterests(
            "https://rw5msr5jwcgllt45oe65ecz74m0ijvsy.lambda-url.eu-west-2.on.aws/",
        Constants.currentUser.UserId)
    }

    suspend fun removeUserInterests(): Response<Int> {
        return RetrofitInstance.newsApi.removeUserInterests(
            "https://2bo6i5o6k6wmk3r7hhqkobwzee0gxzzi.lambda-url.eu-west-2.on.aws/",
        Constants.currentUser.UserId)
    }

    suspend fun addUserInterests(categoriesId: List<Int>): Response<Int> {
        return if(categoriesId.isEmpty()) {
            val list = categoriesId.toMutableList()
            list.add(-1)
            RetrofitInstance.newsApi.addUserInterests(
                "https://d63vttosnx27b2r74zk2itmxtu0nbwuf.lambda-url.eu-west-2.on.aws/",
                list.toList(), Constants.currentUser.UserId)
        } else {
            RetrofitInstance.newsApi.addUserInterests(
                "https://d63vttosnx27b2r74zk2itmxtu0nbwuf.lambda-url.eu-west-2.on.aws/",
                categoriesId, Constants.currentUser.UserId)
        }
    }
}