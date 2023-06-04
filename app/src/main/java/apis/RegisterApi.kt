package apis

import android.view.inspector.IntFlagMapping
import models.User
import models.UserConfiguration
import retrofit2.Response
import retrofit2.http.*

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

    @POST
    suspend fun createUserConfiguration(
        @Url url: String,
        @Query("theme") theme: Int,
        @Query("userId") userId: Int
    ): Response<Int>

    @GET
    suspend fun getUserConfigurationById(
        @Url url: String,
        @Query("userId") userId: Int
    ): Response<UserConfiguration>

    @PUT
    suspend fun updateUserConfiguration(
        @Url url: String,
        @Query("theme") theme: Int,
        @Query("notifications") notifications: Int,
        @Query("configurationId") configurationId: Int
    ): Response<Int>

    @PUT
    suspend fun updateUserInformation(
        @Url url: String,
        @Query("name") name: String,
        @Query("description") description: String,
        @Query("avatarImage") avatarImage: String,
        @Query("userId") userId: Int
    ): Response<Int>
}