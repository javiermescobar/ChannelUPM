package repositories

import apis.RetrofitInstance
import models.User
import retrofit2.Response

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
}