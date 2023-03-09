package apis

import models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface RegisterApi {
    @GET
    suspend fun mailRegistered(
        @Url url: String,
        @Query("mail") mail: String
    ): Response<Int>

    @POST
    suspend fun registerUser(
        @Url url: String,
        @Query("name") name: String,
        @Query("mail") mail: String,
        @Query("password") password: String
    ): Response<User>
}