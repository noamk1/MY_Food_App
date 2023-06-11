package hackeru.noam.my_project.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hackeru.noam.my_project.db.MealDatabase

class MealViewModelFactory(
    private val mealDatabase: MealDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        // Create an instance of MealViewModel and pass the MealDatabase to its constructor
        return MealViewModel(mealDatabase)as T
    }

}