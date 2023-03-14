package repositories

import apis.RetrofitInstance
import models.NewsItem
import retrofit2.Response

class NewsRepository {

    suspend fun getNews(userId: Int): Response<List<NewsItem>> {
        return RetrofitInstance.newsApi.getNews("https://srcoqva7pubpnxu4ix5cdrisda0ecsmu.lambda-url.eu-west-2.on.aws/",
        userId = userId)
    }

    suspend fun addNew(userId: Int, title: String, description: String, categoryId: Int): Response<Int> {
        return RetrofitInstance.newsApi.addNewsItem("https://zndva5soirzksyn3hs62ls4s5y0vqpvx.lambda-url.eu-west-2.on.aws/",
        userId = userId,
        title = title,
        description = description,
        categoryId = categoryId)
    }

    suspend fun editNew(newId: Int, title: String, description: String, categoryId: Int): Response<Int> {
        return RetrofitInstance.newsApi.editNewsItem("https://gkmkxysvl4idar6n57qgs7xkse0bokvs.lambda-url.eu-west-2.on.aws/",
        newId = newId,
        title = title,
        description = description,
        categoryId = categoryId)
    }

}