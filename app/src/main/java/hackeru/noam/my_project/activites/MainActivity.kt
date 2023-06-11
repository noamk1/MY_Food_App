package hackeru.noam.my_project.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import hackeru.noam.my_project.R
import hackeru.noam.my_project.db.MealDatabase
import hackeru.noam.my_project.viewModel.HomeViewModel
import hackeru.noam.my_project.viewModel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

        private lateinit var bottomNavigationView: BottomNavigationView

    // Lazily initialize the HomeViewModel using ViewModelProvider
        val viewModel: HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelProviderFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.btm_nav)


        val navController = Navigation.findNavController(this, R.id.host_fragment)

        // Set up the navigation with the bottom navigation view
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menuFragment -> {
                    val currentDestination = navController.currentDestination
                    if (currentDestination?.id != R.id.menuFragment) {
                        navController.popBackStack(R.id.menuFragment, false)
                    }
                    true
                }
                R.id.favoritesFragment -> {
                    showToast("Swipe right or left to delete a meal :)")
                    navController.navigate(R.id.favoritesFragment)
                    true
                }
                R.id.categoriesFragment -> {
                    navController.navigate(R.id.categoriesFragment)
                    true
                }
                R.id.searchFragment -> {
                    showToast("Here you can search for meals, for example - beef")
                    navController.navigate(R.id.searchFragment)
                    true
                }
                else -> false
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

