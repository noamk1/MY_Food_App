package hackeru.noam.my_project.activites


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import hackeru.noam.my_project.R
import hackeru.noam.my_project.databinding.ActivityMealBinding
import hackeru.noam.my_project.db.MealDatabase
import hackeru.noam.my_project.fragments.MenuFragment
import hackeru.noam.my_project.pojo.Meal
import hackeru.noam.my_project.viewModel.MealViewModel
import hackeru.noam.my_project.viewModel.MealViewModelFactory


class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding:ActivityMealBinding
    private lateinit var mealMvvm:MealViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Create an instance of MealDatabase
        val mealDatabase = MealDatabase.getInstance(this)

        // Create a ViewModelFactory for MealViewModel
        val viewModelFactory = MealViewModelFactory(mealDatabase)

        // Initialize the MealViewModel using ViewModelProvider and the factory
        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        // Get meal information from the intent
        getMealInformationFromIntent()

        // Set the meal information in the views
        setInformationInViews()

        // Set up the loading state of the UI
        loadingCase()

        // Fetch the meal details from the ViewModel
        mealMvvm.getMealDetail(mealId)

        // Observe the meal details LiveData for changes
        observerMealDetailsLiveData()

        // Set up the click listener for the favorite button
        onFavoriteClick()

    }

    private fun onFavoriteClick() {
        binding.btnAddToFav.setOnClickListener {
            // Insert the meal into the database
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal saved", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private var mealToSave:Meal?=null
    private fun observerMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this, object :Observer<Meal>{
            override fun onChanged(t: Meal) {
                // Handle the response and update the UI
                onResponseCase()
                val meal = t
                mealToSave = meal

                // Set the meal details in the views
                binding.tvCategory.text = "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal!!.strArea}"
                binding.tvInstructionsSteps.text = meal.strInstructions
            }

        })

    }

    private fun setInformationInViews() {

        // Load the meal thumbnail using Glide library
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        // Set the meal name as the title in the collapsing toolbar
        binding.collapsingTollbar.title = mealName
        // Set the colors for the collapsed and expanded title
        binding.collapsingTollbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingTollbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }


    private fun getMealInformationFromIntent() {
        // Get the meal ID, name, and thumbnail from the intent extras
        val intent = intent
        mealId = intent.getStringExtra(MenuFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(MenuFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(MenuFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        // Show the progress bar and hide other views
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE



    }

    private fun onResponseCase(){
        // Hide the progress bar and show other views
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE



    }
}