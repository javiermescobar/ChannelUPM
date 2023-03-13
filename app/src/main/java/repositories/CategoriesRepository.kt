package repositories

import apis.RetrofitInstance
import models.Category
import retrofit2.Response

class CategoriesRepository {

    suspend fun getCategories(): Response<List<Category>> {
        return RetrofitInstance.categoriesApi.getCategories(
            "https://e354vrj3pfgbrwisc7lbt43dzi0lgwft.lambda-url.eu-west-2.on.aws/"
        )
    }
}