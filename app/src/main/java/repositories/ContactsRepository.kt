package repositories

import apis.RetrofitInstance
import models.User
import retrofit2.Response

class ContactsRepository {

    suspend fun getAllUsers(): Response<List<User>> {
        return RetrofitInstance.contactsApi.getAllUsers(
            "https://3fyp7tj3elbbrgtyj2mrmllofq0hzbsg.lambda-url.eu-west-2.on.aws/")
    }

    suspend fun searchUser(searchString: String): Response<List<User>> {
        return RetrofitInstance.contactsApi.searchUser(
            "https://agndzlvxfhvqdux2ssox2goxtu0ucymu.lambda-url.eu-west-2.on.aws/",
            searchString)
    }

    suspend fun getContacts(userId: Int): Response<List<User>> {
        return RetrofitInstance.contactsApi.getContacts(
            "https://4h5cjgghl6bptfuundxfwxakwq0nwkmm.lambda-url.eu-west-2.on.aws/",
        userId)
    }

    suspend fun searchContacts(userId: Int, searchString: String): Response<List<User>> {
        return RetrofitInstance.contactsApi.searchContacts(
            "https://fj3dpd3erczdhvflp4gu7piaeu0sewgt.lambda-url.eu-west-2.on.aws/",
            userId, searchString)
    }

    suspend fun saveUser(userId: Int, contactId: Int): Response<Int> {
        return RetrofitInstance.contactsApi.saveUser(
            "https://cb7dzw7icj2c2a4di2i4qculii0dzxpi.lambda-url.eu-west-2.on.aws/",
            userId,
            contactId)
    }
}