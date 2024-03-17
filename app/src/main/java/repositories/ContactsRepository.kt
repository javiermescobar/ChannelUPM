package repositories

import apis.RetrofitInstance
import models.User
import retrofit2.Response

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

    suspend fun saveUser(userId: Int, contactId: Int): Response<Int> {
        return RetrofitInstance.contactsApi.saveUser(
            "https://nnpdacnxdhxilyhcijhmpzruwe0cvusm.lambda-url.eu-west-2.on.aws/",
            userId, contactId)
    }
}