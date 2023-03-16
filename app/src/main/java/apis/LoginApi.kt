package apis

import models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface LoginApi {

    @GET
    suspend fun loginUser(
        @Url url: String,
        @Query("mail") mail: String,
        @Query("password") password: String): Response<Int>

    @GET
    suspend fun getUserById(
        @Url url: String,
        @Query("userId") userId: Int): Response<User>
}