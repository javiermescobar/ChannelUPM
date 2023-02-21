package apis

import models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface LoginApi {

    @GET
    suspend fun LoginUser(
        @Url url: String,
        @Query("mail") mail: String,
        @Query("password") password: String): Response<User>
}