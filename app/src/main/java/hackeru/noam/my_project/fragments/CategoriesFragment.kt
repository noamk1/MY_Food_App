package hackeru.noam.my_project.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import hackeru.noam.my_project.activites.CategoryMealsActivity
import hackeru.noam.my_project.activites.MainActivity
import hackeru.noam.my_project.adapters.CategoriesAdapter
import hackeru.noam.my_project.databinding.FragmentCategoriesBinding
import hackeru.noam.my_project.viewModel.HomeViewModel


class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter:CategoriesAdapter
    private lateinit var viewModel: HomeViewModel




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the view model
        viewModel = (activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Prepare the RecyclerView for categories
        prepareCategoriesRecyclerView()

        // Retrieve categories from the view model
        viewModel.getCategories()

        // Observe changes in the categories LiveData and update the adapter
        observeCategoriesLiveData()

        // Handle click events on categories
        onCategoryClick()
    }



    private fun onCategoryClick() {
        categoriesAdapter.onItemClick = {category ->
            val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(MenuFragment.CATEGORY_NAME, category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context, 2 , GridLayoutManager.VERTICAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories->
            // Update the category list in the adapter
            categoriesAdapter.setCategoryList(categories)
        })
    }

}