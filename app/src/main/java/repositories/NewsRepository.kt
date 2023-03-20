package repositories

import apis.RetrofitInstance
import models.NewsItem
import retrofit2.Response

class NewsRepository {

    suspend fun getNews(userId: Int): Response<List<NewsItem>> {
        return RetrofitInstance.newsApi.getNews("https://srcoqva7pubpnxu4ix5cdrisda0ecsmu.lambda-url.eu-west-2.on.aws/",
        userId = userId)
    }

    suspend fun addNewsItem(userId: Int, title: String, description: String, categoryId: Int): Response<Int> {
        return RetrofitInstance.newsApi.addNewsItem("https://vrkjzox5krfbqas46wwxqnzbvu0gvfbh.lambda-url.eu-west-2.on.aws/",
        userId = userId,
        title = title,
        description = description,
        categoryId = categoryId)
    }

    suspend fun editNewsItem(newId: Int, title: String, description: String, categoryId: Int): Response<Int> {
        return RetrofitInstance.newsApi.editNewsItem("https://gtxo356dspiz3xy2gl4fpdyyte0yjrlp.lambda-url.eu-west-2.on.aws/",
        newId = newId,
        title = title,
        description = description,
        categoryId = categoryId)
    }

}