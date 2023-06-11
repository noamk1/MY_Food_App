package hackeru.noam.my_project.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hackeru.noam.my_project.db.MealDatabase

class HomeViewModelFactory(
    private val mealDatabase: MealDatabase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Create and return an instance of HomeViewModel with the provided MealDatabase
        return HomeViewModel(mealDatabase)as T
    }

}