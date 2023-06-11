package hackeru.noam.my_project.retrofit

import hackeru.noam.my_project.pojo.CategoryList
import hackeru.noam.my_project.pojo.MealsByCategoryList
import hackeru.noam.my_project.pojo.MealList
import hackeru.noam.my_project.pojo.MealsByCategory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {




    @GET("lookup.php")
    fun getMealDetails(@Query("i") id:String): Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName:String) : Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String):Call<MealsByCategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s")searchQuery:String):Call<MealList>
}