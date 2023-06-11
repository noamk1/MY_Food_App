package hackeru.noam.my_project.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hackeru.noam.my_project.db.MealDatabase
import hackeru.noam.my_project.pojo.*
import hackeru.noam.my_project.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase: MealDatabase
): ViewModel() {

    // LiveData for menu items, categories, favorites meals, and searched meals
    private var menuItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoritesMealsLiveData = mealDatabase.mealDao().getAllMeals()
    private var searchedMealsLiveData = MutableLiveData<List<Meal>>()



    // Function to fetch menu items from the API
    fun getMenuItems(){
        RetrofitInstance.api.getPopularItems("Beef").enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if (response.body() != null){
                    // Update the MutableLiveData with the fetched menu items
                    menuItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                // Handle API call failure and log the error message
                Log.d("MenuFragment", t.message.toString())
            }

        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                response.body()?.let { categoryList ->
                    // Update the MutableLiveData with the fetched categories
                    categoriesLiveData.postValue(categoryList.categories)
                }

            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                // Handle API call failure and log the error message
                Log.e("HomeViewModel", t.message.toString())
            }

        })
    }


    // Function to delete a meal from the local database using coroutines
    fun deleteMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    // Function to insert a meal in the local database using coroutines
    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().upsert(meal)
        }
    }

    // Function to search meals based on a search query
    fun searchMeals(searchQuery:String) = RetrofitInstance.api.searchMeals(searchQuery).enqueue(
            object : Callback<MealList>{
                override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                    val mealsList = response.body()?.meals
                    mealsList?.let {
                        // Update the MutableLiveData with the searched meals
                        searchedMealsLiveData.postValue(it)
                    }
                }

                override fun onFailure(call: Call<MealList>, t: Throwable) {
                    // Handle API call failure and log the error message
                    Log.e("HomeViewModel", t.message.toString())
                }

            }
    )


    // Function to observe the LiveData for searched meals
    fun observeSearchedMealsLiveData():LiveData<List<Meal>> = searchedMealsLiveData

    // Function to observe the LiveData for menu items
    fun observeMenuItemsLiveData():LiveData<List<MealsByCategory>>{
        return menuItemsLiveData
    }

    // Function to observe the LiveData for categories
    fun observeCategoriesLiveData():LiveData<List<Category>>{
        return categoriesLiveData
    }

    // Function to observe the LiveData for favorite meals
    fun observeFavoritesMealsLiveData():LiveData<List<Meal>>{
        return favoritesMealsLiveData
    }

}