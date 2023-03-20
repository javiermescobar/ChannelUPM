package repositories

import apis.RetrofitInstance
import models.User
import retrofit2.Response

class LoginRepository {

    suspend fun loginUser(mail: String, password: String): Response<Int> {
        return RetrofitInstance.loginApi.loginUser(
            "https://wbnb52x274aiftuqkufc2j2uvy0uwyho.lambda-url.eu-west-2.on.aws/",
        mail = mail, password = password)
    }

    suspend fun getUserById(userId: Int): Response<User> {
        return RetrofitInstance.loginApi.getUserById(
            "https://if5ss3htn2fts4dd4b2b7wxvkq0knqpd.lambda-url.eu-west-2.on.aws/",
        userId = userId)
    }
}