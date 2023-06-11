package hackeru.noam.my_project.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import hackeru.noam.my_project.R
import hackeru.noam.my_project.activites.MainActivity
import hackeru.noam.my_project.activites.MealActivity
import hackeru.noam.my_project.adapters.MenuAdapter
import hackeru.noam.my_project.databinding.FragmentMenuBinding
import hackeru.noam.my_project.pojo.MealsByCategory
import hackeru.noam.my_project.viewModel.HomeViewModel

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var menuItemsAdapter:MenuAdapter


    companion object{
        const val MEAL_ID = "hackeru.noam.my_project.fragments.idMeal"
        const val MEAL_NAME = "hackeru.noam.my_project.fragments.nameMeal"
        const val MEAL_THUMB = "hackeru.noam.my_project.fragments.thumbMeal"
        const val CATEGORY_NAME = "hackeru.noam.my_project.fragments.categoryName"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the view model
        viewModel = (activity as MainActivity).viewModel

        // Initialize the adapter for the menu items
        menuItemsAdapter = MenuAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Prepare the RecyclerView for menu items
        prepareMenuItemsRecyclerView()

        // Retrieve menu items from the view model
        viewModel.getMenuItems()

        // Observe changes in the menu items LiveData and update the adapter
        observeMenuItemsLiveData()

        // Handle click events on menu items
        onMenuItemClick()

        // Handle click event on the search icon
        onSearchIconClick()


    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }
    }



    private fun onMenuItemClick() {
        menuItemsAdapter.onItemClick = {meal->
            val intent = Intent(activity, MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun prepareMenuItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = GridLayoutManager(activity, 2 , GridLayoutManager.VERTICAL, false)
            adapter = menuItemsAdapter
        }
    }

    private fun observeMenuItemsLiveData() {
        viewModel.observeMenuItemsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            // Update the menu items in the adapter
            menuItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)

        }
    }


}