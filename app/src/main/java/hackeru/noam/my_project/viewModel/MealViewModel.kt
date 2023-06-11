package hackeru.noam.my_project.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hackeru.noam.my_project.db.MealDatabase
import hackeru.noam.my_project.pojo.Meal
import hackeru.noam.my_project.pojo.MealList
import hackeru.noam.my_project.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
   val mealDatabase:MealDatabase
):ViewModel() {
    private var mealDetailsLiveData = MutableLiveData<Meal>()


    // Function to fetch meal details from the API based on meal ID
    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetails(id).enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body()!=null){
                    // Update the MutableLiveData with the fetched meal details
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }
                else
                // Handle unsuccessful response or empty body
                // You can customize this part based on your error handling requirements
                    return
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                // Handle API call failure and log the error message
                Log.d("MealActivity", t.message.toString())
            }
        })
    }


    // Function to observe the LiveData for meal details
    fun observerMealDetailsLiveData():LiveData<Meal>{
        return mealDetailsLiveData
    }

    // Function to insert a meal into the local database using coroutines
    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }



}