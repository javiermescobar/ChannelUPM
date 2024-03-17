package repositories

import apis.RetrofitInstance
import models.User
import retrofit2.Response

class LoginRepository {

    suspend fun loginUser(mail: String, password: String): Response<Int> {
        return RetrofitInstance.loginApi.loginUser(
            "https://e3vnn72eguls5idmoamzbgw6le0zhteh.lambda-url.eu-west-2.on.aws/",
            mail, password)
    }

    suspend fun getUserById(userId: Int): Response<User> {
        return RetrofitInstance.loginApi.getUserById(
            "https://2cssj35kgq6ss4bpfxrx5w4yqq0iimpt.lambda-url.eu-west-2.on.aws/",
            userId)
    }
}