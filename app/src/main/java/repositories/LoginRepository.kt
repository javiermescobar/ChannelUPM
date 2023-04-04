package repositories

import apis.RetrofitInstance
import models.User
import retrofit2.Response

class LoginRepository {

    suspend fun loginUser(mail: String, password: String): Response<Int> {
        return RetrofitInstance.loginApi.loginUser(
            "https://zzp4c5q2kzpxgqfz6mti5wm3qi0ldmsn.lambda-url.eu-west-2.on.aws/",
        mail = mail, password = password)
    }

    suspend fun getUserById(userId: Int): Response<User> {
        return RetrofitInstance.loginApi.getUserById(
            "https://h7nyuqj6ibqkgogdo5jmqqjqny0swsvs.lambda-url.eu-west-2.on.aws/",
        userId = userId)
    }
}