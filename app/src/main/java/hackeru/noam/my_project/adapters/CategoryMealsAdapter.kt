package hackeru.noam.my_project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hackeru.noam.my_project.databinding.MealItemBinding
import hackeru.noam.my_project.pojo.MealsByCategory

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.CategoryMealsViewModel>() {
    private var mealsList =ArrayList<MealsByCategory>()


    // Set the meals list
    fun setMealsList(mealsList: List<MealsByCategory>){
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    inner class CategoryMealsViewModel(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealsViewModel {
        // Create a ViewHolder by inflating the item layout using MealItemBinding
        return CategoryMealsViewModel(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryMealsViewModel, position: Int) {

        // Load the meal image using Glide
        Glide.with(holder.itemView).load(mealsList[position].strMealThumb).into(holder.binding.imgMeal)

        // Set the meal name
        holder.binding.tvMealName.text = mealsList[position].strMeal

    }
    override fun getItemCount(): Int {
        // Return the number of items in the list
        return mealsList.size
    }



}