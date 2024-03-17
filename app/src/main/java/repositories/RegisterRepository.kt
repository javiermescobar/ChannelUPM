package repositories

import apis.RetrofitInstance
import models.User
import models.UserConfiguration
import retrofit2.Response
import utils.Constants

class RegisterRepository {

    suspend fun mailRegistered(mail: String): Response<Int> {
        return RetrofitInstance.registerApi.mailRegistered(
            "https://t4w5qgajvq3wiums4utwk6ay5m0xpihl.lambda-url.eu-west-2.on.aws/",
            mail)
    }

    suspend fun registerUser(name: String, mail: String, password: String): Response<User> {
        return RetrofitInstance.registerApi.registerUser(
            "https://oetncpakcrfchajqrubfwug6ne0kbsfr.lambda-url.eu-west-2.on.aws/",
            name, mail, password)
    }

    suspend fun createUserConfiguration(theme: Int, userId: Int): Response<Int> {
        return RetrofitInstance.registerApi.createUserConfiguration(
            "https://i5tucvnguvhm2htx46ksqbxiky0gfhzg.lambda-url.eu-west-2.on.aws/",
            theme, userId)
    }

    suspend fun getUserConfigurationById(userId: Int): Response<UserConfiguration> {
        return RetrofitInstance.registerApi.getUserConfigurationById(
            "https://x4ky2ljoyuzuwc3gnsm332l4pq0jxsft.lambda-url.eu-west-2.on.aws/",
            userId)
    }

    suspend fun updateUserConfiguration(theme: Int, notifications: Int, configurationId: Int): Response<Int> {
        return RetrofitInstance.registerApi.updateUserConfiguration(
            "https://6574dbzw6qul5cb6fqwiireecu0cdjof.lambda-url.eu-west-2.on.aws/",
            theme, notifications, configurationId)
    }

    suspend fun updateUserInformation(name: String, description: String, avatarImage: String): Response<Int> {
        return RetrofitInstance.registerApi.updateUserInformation(
            "https://vjr5pjnve3lknbfv63rhdovfyu0ihhmu.lambda-url.eu-west-2.on.aws/",
            name, description, avatarImage, Constants.currentUser.UserId)
    }
}