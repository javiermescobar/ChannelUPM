package repositories

import apis.RetrofitInstance
import models.User
import retrofit2.Response

class ContactsRepository {

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
}