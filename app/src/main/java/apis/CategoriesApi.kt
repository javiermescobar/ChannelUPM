package apis

import models.Category
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface CategoriesApi {

    @GET
    suspend fun getCategories(
        @Url url: String
    ): Response<List<Category>>
}