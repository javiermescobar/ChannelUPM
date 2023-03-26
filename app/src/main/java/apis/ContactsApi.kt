package apis

import models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ContactsApi {

    @GET
    suspend fun getContacts(
        @Url url: String,
        @Query("userId") userId: Int
        ): Response<List<User>>
}