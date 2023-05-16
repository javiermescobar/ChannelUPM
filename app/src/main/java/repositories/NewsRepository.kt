package repositories

import apis.RetrofitInstance
import models.NewsItem
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime

class NewsRepository {

    suspend fun getNews(userId: Int): Response<List<NewsItem>> {
        return RetrofitInstance.newsApi.getNews("https://wfu532amhqr4s6xskywzkcwtzy0ugimn.lambda-url.eu-west-2.on.aws/",
        userId = userId)
    }

    suspend fun addNewsItem(userId: Int, title: String, date: String, description: String, categoryId: Int): Response<Int> {
        return RetrofitInstance.newsApi.addNewsItem("https://5ezpzqmafccz6nmzejsmwozr2m0nbdto.lambda-url.eu-west-2.on.aws/",
            userId = userId,
            title = title,
            date = date,
            description = description,
            categoryId = categoryId)
    }

    suspend fun editNewsItem(newId: Int, title: String, description: String, categoryId: Int): Response<Int> {
        return RetrofitInstance.newsApi.editNewsItem("https://kla55kqkpfixeylzoxiuwje6bu0wcjsm.lambda-url.eu-west-2.on.aws/",
        newId = newId,
        title = title,
        description = description,
        categoryId = categoryId)
    }

}