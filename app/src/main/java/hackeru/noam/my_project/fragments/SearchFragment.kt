package hackeru.noam.my_project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import hackeru.noam.my_project.activites.MainActivity
import hackeru.noam.my_project.adapters.FavoritesAdapter
import hackeru.noam.my_project.databinding.FragmentSearchBinding
import hackeru.noam.my_project.viewModel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var searchRecyclerviewAdapter:FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the view model
        viewModel =(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Prepare the RecyclerView for searched meals
        prepareRecyclerView()

        // Handle search button click
        binding.imgSearchArrow.setOnClickListener {searchMeals()}

        // Observe changes in the searched meals LiveData and update the adapter
        observeSearchedMealsLiveData()

        var searchJob :Job? =null
        binding.edSearchBox.addTextChangedListener{searchQuery ->
            searchJob?.cancel()
            searchJob =lifecycleScope.launch {
                delay(100)
                viewModel.searchMeals(searchQuery.toString())
            }
        }
    }

    private fun observeSearchedMealsLiveData() {
        viewModel.observeSearchedMealsLiveData().observe(viewLifecycleOwner, Observer { mealsList->
            // Update the searched meals in the adapter
            searchRecyclerviewAdapter.differ.submitList(mealsList)
        })
    }

    private fun searchMeals() {
        val searchQuery = binding.edSearchBox.text.toString()
        if (searchQuery.isNotEmpty()){
            // Perform the search
            viewModel.searchMeals(searchQuery)
        }
    }

    private fun prepareRecyclerView() {
        searchRecyclerviewAdapter = FavoritesAdapter()
        binding.rvSearchedMeals.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false )
            adapter = searchRecyclerviewAdapter
        }
    }

}