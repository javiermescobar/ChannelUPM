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
}