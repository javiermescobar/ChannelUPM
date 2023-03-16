package repositories

import apis.RetrofitInstance
import models.User
import retrofit2.Response

class RegisterRepository {

    suspend fun mailRegistered(mail: String): Response<Int> {
        return RetrofitInstance.registerApi.mailRegistered(
            "https://mpjewtqhgn3ukl2icg5caundoq0nxjzp.lambda-url.eu-west-2.on.aws/",
            mail)
    }

    suspend fun registerUser(name: String, mail: String, password: String): Response<User> {
        return RetrofitInstance.registerApi.registerUser(
            "https://vrhsffvcmua6xbiimvpw6jjure0mzxxo.lambda-url.eu-west-2.on.aws/",
            name = name, mail = mail, password = password)
    }
}