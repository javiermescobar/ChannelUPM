package repositories

import apis.RetrofitInstance
import models.Category
import retrofit2.Response

class CategoriesRepository {

    suspend fun getCategories(): Response<List<Category>> {
        return RetrofitInstance.categoriesApi.getCategories(
            "https://5nqx6ocntyyb2y6srnue2x2g640qwssh.lambda-url.eu-west-2.on.aws/"
        )
    }
}