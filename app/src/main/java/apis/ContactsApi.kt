package apis

import models.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ContactsApi {

    @GET
    suspend fun getAllUsers(
        @Url url: String): Response<List<User>>

    @GET
    suspend fun searchUser(
        @Url url: String,
        @Query("searchString") searchString: String): Response<List<User>>

    @GET
    suspend fun getContacts(
        @Url url: String,
        @Query("userId") userId: Int): Response<List<User>>

    @GET
    suspend fun searchContacts(
        @Url url: String,
        @Query("userId") userId: Int,
        @Query("searchString") searchString: String): Response<List<User>>
}