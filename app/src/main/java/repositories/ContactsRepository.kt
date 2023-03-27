package repositories

import apis.RetrofitInstance
import models.User
import retrofit2.Response

class ContactsRepository {

    suspend fun getContacts(userId: Int): Response<List<User>> {
        return RetrofitInstance.contactsApi.getContacts(
            "https://2rulguyxcraos2zmzzgwzot6bi0nqyga.lambda-url.eu-west-2.on.aws/",
        userId)
    }

    suspend fun searchContacts(userId: Int, searchString: String): Response<List<User>> {
        return RetrofitInstance.contactsApi.searchContacts(
            "https://d7lj25jy4aejvz7463fkkpo2ya0lmyvi.lambda-url.eu-west-2.on.aws/",
            userId, searchString)
    }
}