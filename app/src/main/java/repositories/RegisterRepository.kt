package repositories

import apis.RetrofitInstance
import models.User
import models.UserConfiguration
import retrofit2.Response
import utils.Constants

class RegisterRepository {

    suspend fun mailRegistered(mail: String): Response<Int> {
        return RetrofitInstance.registerApi.mailRegistered(
            "https://lnymgt6dswbf7khoktascz5psa0wlkhh.lambda-url.eu-west-2.on.aws/",
            mail)
    }

    suspend fun registerUser(name: String, mail: String, password: String): Response<User> {
        return RetrofitInstance.registerApi.registerUser(
            "https://ol7y3ipq3shcpn66egwd5tts740zjmnj.lambda-url.eu-west-2.on.aws/",
            name = name, mail = mail, password = password)
    }

    suspend fun createUserConfiguration(theme: Int, userId: Int): Response<Int> {
        return RetrofitInstance.registerApi.createUserConfiguration(
            "https://ls5lxpanmvohs6xgoa4zn75fwm0rxagh.lambda-url.eu-west-2.on.aws/",
            theme, userId)
    }

    suspend fun getUserConfigurationById(userId: Int): Response<UserConfiguration> {
        return RetrofitInstance.registerApi.getUserConfigurationById(
            "https://xzccfoho56ewmmc6l7tt76lieq0forri.lambda-url.eu-west-2.on.aws/",
            userId)
    }

    suspend fun updateUserConfiguration(theme: Int, notifications: Int, configurationId: Int): Response<Int> {
        return RetrofitInstance.registerApi.updateUserConfiguration(
            "https://vns4o5ykkjs35rp2ltidxdsvhy0dwvhu.lambda-url.eu-west-2.on.aws/",
            theme, notifications, configurationId)
    }

    suspend fun updateUserInformation(name: String, description: String, avatarImage: String): Response<Int> {
        return RetrofitInstance.registerApi.updateUserInformation(
            "https://6xk7sye3am575ibw4zmdn745aq0cjfsi.lambda-url.eu-west-2.on.aws/",
            name, description, avatarImage, Constants.currentUser.UserId)
    }
}