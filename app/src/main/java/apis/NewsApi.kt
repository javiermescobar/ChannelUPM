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
        @Query("categoryId") categoryId: Int,
        @Query("categoryName") categoryName: String
    ): Response<Int>

    @PUT
    suspend fun editNewsItem(
        @Url url: String,
        @Query("newsItemId") newId: Int,
        @Query("title") title: String,
        @Query("description") description: String,
        @Query("categoryId") categoryId: Int
    ): Response<Int>

    @GET
    suspend fun getUserInterests(
        @Url url: String,
        @Query("userId") userId: Int
    ): Response<List<Int>>

    @DELETE
    suspend fun removeUserInterests(
        @Url url: String,
        @Query("userId") userId: Int
    ): Response<Int>

    @POST
    suspend fun addUserInterests(
        @Url url: String,
        @Query("categoriesId") categoriesId: List<Int>,
        @Query("userId") userId: Int
    ): Response<Int>
}