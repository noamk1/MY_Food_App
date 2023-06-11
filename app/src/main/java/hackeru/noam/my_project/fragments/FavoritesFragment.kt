package hackeru.noam.my_project.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import hackeru.noam.my_project.activites.MainActivity
import hackeru.noam.my_project.activites.MealActivity
import hackeru.noam.my_project.adapters.FavoritesAdapter
import hackeru.noam.my_project.databinding.FragmentFavoritesBinding
import hackeru.noam.my_project.pojo.MealsByCategory
import hackeru.noam.my_project.viewModel.HomeViewModel


class FavoritesFragment : Fragment() {
    private lateinit var binding:FragmentFavoritesBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var favoritesAdapter:FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the view model
        viewModel = (activity as MainActivity).viewModel

        // Initialize the adapter for favorites
        favoritesAdapter = FavoritesAdapter()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Prepare the RecyclerView for favorites
        prepareRecyclerView()

        // Observe changes in the favorites LiveData and update the adapter
        observeFavorites()

        // Configure item swipe actions for deletion
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deleteMeal = favoritesAdapter.differ.currentList[position]
                viewModel.deleteMeal(deleteMeal)

                // Show a snackbar to allow undoing the deletion
                Snackbar.make(requireView(),"Meal Deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo", View.OnClickListener {
                        viewModel.insertMeal(deleteMeal)
                    }
                ).show()
            }

        }
        // Attach the item touch helper to the RecyclerView
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.rvFavorites)

    }


    private fun prepareRecyclerView() {
        favoritesAdapter = FavoritesAdapter()
        binding.rvFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }
    }

    private fun observeFavorites() {
        viewModel.observeFavoritesMealsLiveData().observe(viewLifecycleOwner, Observer { meals->
            // Update the favorites in the adapter
            favoritesAdapter.differ.submitList(meals)

        })
    }



}