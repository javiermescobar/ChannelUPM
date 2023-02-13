package apis

import models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface UserApi {
    @GET
    suspend fun getUser(@Url url: String): Response<User>
}