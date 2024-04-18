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
        return RetrofitInstance.newsApi.getNews("https://jtovok76uu2bdrf6veia3quxgi0kannk.lambda-url.eu-west-2.on.aws/",
            userId)
    }

    suspend fun addNewsItem(userId: Int, title: String, date: String, description: String, category: InteractiveCategory): Response<Int> {
        return RetrofitInstance.newsApi.addNewsItem("https://vu7a7islms7li2vvsu672b3aaa0jrnaf.lambda-url.eu-west-2.on.aws/",
            userId, title, description, date, category.categoryId, category.categoryTitle)
    }

    suspend fun sendNewsNotifications(categoryId: Int, categoryName: String): Response<Int> {
        return RetrofitInstance.newsApi.sendNewsNotifications(
            "https://jcbn2kswrenelwqoxwgmomv2tu0agrlh.lambda-url.eu-west-2.on.aws/",
            categoryId, categoryName)
    }

    suspend fun editNewsItem(newId: Int, title: String, description: String, categoryId: Int): Response<Int> {
        return RetrofitInstance.newsApi.editNewsItem("https://f7kzppr3rd3bey6zcfodazevrq0nuavh.lambda-url.eu-west-2.on.aws/",
        newId, title, description, categoryId)
    }

    suspend fun getUserInterests(): Response<List<Int>> {
        return RetrofitInstance.newsApi.getUserInterests(
            "https://z7rgdrhr5bwkfcnt2hkblac6fu0ggkpa.lambda-url.eu-west-2.on.aws/",
        Constants.currentUser.UserId)
    }

    suspend fun removeUserInterests(): Response<Int> {
        return RetrofitInstance.newsApi.removeUserInterests(
            "https://7fygtvoms63ly4figlgftjzfra0epzga.lambda-url.eu-west-2.on.aws/",
        Constants.currentUser.UserId)
    }

    suspend fun addUserInterests(categoriesId: List<Int>): Response<Int> {
        return if(categoriesId.isEmpty()) {
            RetrofitInstance.newsApi.addUserInterests(
                "https://k4vek2gdfha35ge77ihg65ioue0dgpgl.lambda-url.eu-west-2.on.aws/",
                listOf(-1), Constants.currentUser.UserId)
        } else {
            RetrofitInstance.newsApi.addUserInterests(
                "https://k4vek2gdfha35ge77ihg65ioue0dgpgl.lambda-url.eu-west-2.on.aws/",
                categoriesId, Constants.currentUser.UserId)
        }
    }
}