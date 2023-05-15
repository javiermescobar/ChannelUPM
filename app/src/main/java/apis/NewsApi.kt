package apis

import models.NewsItem
import retrofit2.Response
import retrofit2.http.*

interface NewsApi {

    @GET
    suspend fun getNews(
        @Url url: String,
        @Query("userId") userId: Int
    ): Response<List<NewsItem>>

    @POST
    suspend fun addNewsItem(
        @Url url: String,
        @Query("userId") userId: Int,
        @Query("title") title: String,
        @Query("description") description: String,
        @Query("date") date: String,
        @Query("categoryId") categoryId: Int
    ): Response<Int>

    @PUT
    suspend fun editNewsItem(
        @Url url: String,
        @Query("newsItemId") newId: Int,
        @Query("title") title: String,
        @Query("description") description: String,
        @Query("categoryId") categoryId: Int
    ): Response<Int>
}