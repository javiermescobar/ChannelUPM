package repositories

import apis.RetrofitInstance
import models.User
import retrofit2.Response

class UserRepository {

    suspend fun getUser(): Response<User> {
        return RetrofitInstance.userApi.getUser("https://grzh5axoqf44tycdzrz3gjnwsq0jsrib.lambda-url.eu-west-2.on.aws/")
    }
}