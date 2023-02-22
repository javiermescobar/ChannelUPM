package repositories

import apis.RetrofitInstance
import models.User
import retrofit2.Call
import retrofit2.Response

class LoginRepository {

    suspend fun LoginUser(mail: String, password: String): Response<User> {
        return RetrofitInstance.loginApi.LoginUser("https://wbnb52x274aiftuqkufc2j2uvy0uwyho.lambda-url.eu-west-2.on.aws/",
        mail = mail, password = password)
    }
}