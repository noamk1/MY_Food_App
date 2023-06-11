package hackeru.noam.my_project.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import hackeru.noam.my_project.adapters.CategoryMealsAdapter
import hackeru.noam.my_project.databinding.ActivityCategoryMealsBinding
import hackeru.noam.my_project.fragments.MenuFragment
import hackeru.noam.my_project.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    lateinit var binding : ActivityCategoryMealsBinding
    lateinit var categoryMealsViewModel:CategoryMealsViewModel
    lateinit var categoryMealsAdapter:CategoryMealsAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Prepare the RecyclerView
        prepareRecyclerView()

        // Create an instance of CategoryMealsViewModel using ViewModelProviders
        categoryMealsViewModel = ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        // Fetch the meals for the selected category
        categoryMealsViewModel.getMealsByCategory(intent.getStringExtra(MenuFragment.CATEGORY_NAME)!!)

        // Observe the meals LiveData for changes
        categoryMealsViewModel.observeMealsLiveData().observe(this, Observer { mealsList->
            // Update the category count text view
            binding.tvCategoryCount.text = mealsList.size.toString()
            // Set the meals list in the adapter
            categoryMealsAdapter.setMealsList(mealsList)
        })



    }

    private fun prepareRecyclerView() {
        // Create an instance of CategoryMealsAdapter
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.rvMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }
}