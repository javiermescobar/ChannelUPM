package repositories

import apis.RetrofitInstance
import models.User
import retrofit2.Response
import utils.Constants

class ContactsRepository {

    suspend fun getAllUsers(): Response<List<User>> {
        return RetrofitInstance.contactsApi.getAllUsers(
            "https://gvggij32mcwsh3aaeorogj273a0cyrbk.lambda-url.eu-west-2.on.aws/")
    }

    suspend fun searchUser(searchString: String): Response<List<User>> {
        return RetrofitInstance.contactsApi.searchUser(
            "https://jubvdivymbwzm3d235kzjrufu40yiuqw.lambda-url.eu-west-2.on.aws/",
            searchString)
    }

    suspend fun getContacts(userId: Int): Response<List<User>> {
        return RetrofitInstance.contactsApi.getContacts(
            "https://ybrm2zg6gbpbj6dga4emsddrvi0rfedi.lambda-url.eu-west-2.on.aws/",
            userId)
    }

    suspend fun searchContacts(userId: Int, searchString: String): Response<List<User>> {
        return RetrofitInstance.contactsApi.searchContacts(
            "https://gacysizver5ozj4t5xdmofpwoa0jhfgc.lambda-url.eu-west-2.on.aws/",
            userId, searchString)
    }

    suspend fun isContactFromUser(contactId: Int): Response<Int> {
        return RetrofitInstance.contactsApi.isContactFromUser(
            "https://pntnom6msj3nolk6rbzhd7eluu0cxpli.lambda-url.eu-west-2.on.aws/",
            Constants.currentUser.UserId, contactId)
    }

    suspend fun saveUser(userId: Int, contactId: Int): Response<Int> {
        return RetrofitInstance.contactsApi.saveUser(
            "https://nnpdacnxdhxilyhcijhmpzruwe0cvusm.lambda-url.eu-west-2.on.aws/",
            userId, contactId)
    }

    suspend fun removeContact(userId: Int, contactId: Int): Response<Int> {
        return RetrofitInstance.contactsApi.removeContact(
            "https://yp5lruuhewkcs6it2m5gqftzae0tdntg.lambda-url.eu-west-2.on.aws/",
            userId, contactId)
    }
}