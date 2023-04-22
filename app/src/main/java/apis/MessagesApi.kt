package apis

import models.PrivateMessage
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface MessagesApi {

    @GET
    suspend fun getPrivateMessages(
        @Url url: String,
        @Query("userId") userId: Int,
        @Query("contactId") contactId: Int): Response<List<PrivateMessage>>

    @POST
    suspend fun sendPrivateMessage(
        @Url url: String,
        @Query("text") text: String,
        @Query("senderId") senderId: Int,
        @Query("receiverId") receiverId: Int
    ): Response<Int>
}