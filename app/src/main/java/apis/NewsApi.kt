package apis

import models.New
import retrofit2.Response
import retrofit2.http.*

interface NewsApi {

    @GET
    suspend fun getNews(
        @Url url: String,
        @Query("userId") userId: Int
    ): Response<List<New>>

    @POST
    suspend fun addNew(
        @Url url: String,
        @Query("userId") userId: Int,
        @Query("title") title: String,
        @Query("description") description: String,
        @Query("categoryId") categoryId: Int
    ): Response<Int>

    @PUT
    suspend fun editNew(
        @Url url: String,
        @Query("newId") newId: Int,
        @Query("title") title: String,
        @Query("description") description: String,
        @Query("categoryId") categoryId: Int
    ): Response<Int>
}