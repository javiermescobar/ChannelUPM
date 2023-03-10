package apis

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import utils.Constants.Companion.BASE_URL

object RetrofitInstance {

    val retrofit: Retrofit by lazy {

        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val registerApi: RegisterApi by lazy {
        retrofit.create(RegisterApi::class.java)
    }

    val loginApi: LoginApi by lazy {
        retrofit.create(LoginApi::class.java)
    }

    val newsApi: NewsApi by lazy {
        retrofit.create(NewsApi::class.java)
    }

    val categoriesApi: CategoriesApi by lazy {
        retrofit.create(CategoriesApi::class.java)
    }
}