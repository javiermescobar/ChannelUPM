package repositories

import apis.RetrofitInstance
import models.Category
import retrofit2.Response

class CategoriesRepository {

    suspend fun getCategories(): Response<List<Category>> {
        return RetrofitInstance.categoriesApi.getCategories(
            "https://gatdfsi6ktoxuuojhsvb64j3ri0zunvj.lambda-url.eu-west-2.on.aws/"
        )
    }
}