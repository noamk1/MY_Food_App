package hackeru.noam.my_project.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hackeru.noam.my_project.pojo.MealsByCategory
import hackeru.noam.my_project.pojo.MealsByCategoryList
import hackeru.noam.my_project.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel :ViewModel() {

    // LiveData to hold the list of meals for a specific category
    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    // Function to fetch meals by category from the API
    fun getMealsByCategory(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let { mealsList->
                    // Update the MutableLiveData with the fetched meals by category
                    mealsLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                // Handle API call failure and log the error message
               Log.e("CategoryMealsViewModel", t.message.toString())
            }
        })
    }

    // Function to observe the LiveData for meals by category
    fun observeMealsLiveData():LiveData<List<MealsByCategory>>{
        return mealsLiveData
    }
}