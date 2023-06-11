package hackeru.noam.my_project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hackeru.noam.my_project.databinding.MenuItemsBinding

import hackeru.noam.my_project.pojo.MealsByCategory

class MenuAdapter():RecyclerView.Adapter<MenuAdapter.MenuMealViewHolder>() {
    lateinit var onItemClick:((MealsByCategory)-> Unit)
    private var mealList = ArrayList<MealsByCategory>()

    // Function to set the list of meals
    fun setMeals(mealsList: ArrayList<MealsByCategory>){
        this.mealList = mealsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuMealViewHolder {
        // Create a ViewHolder by inflating the item layout using MenuItemsBinding
        return MenuMealViewHolder(
            MenuItemsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false

            )
        )
    }

    override fun onBindViewHolder(holder: MenuMealViewHolder, position: Int) {
        // Load the meal image using Glide
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }
    }

    override fun getItemCount(): Int {
        // Return the number of items in the list
        return mealList.size

    }

    // ViewHolder class for holding the item views
    class MenuMealViewHolder(var binding: MenuItemsBinding):RecyclerView.ViewHolder(binding.root)
}