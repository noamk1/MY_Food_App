package hackeru.noam.my_project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hackeru.noam.my_project.databinding.MealItemBinding
import hackeru.noam.my_project.pojo.Meal


 class FavoritesAdapter :RecyclerView.Adapter<FavoritesAdapter.FavoritesMealsAdapterViewHolder>(){


     // DiffUtil to calculate the difference between old and new items in the list
     private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
            return oldItem == newItem
        }
    }

     // AsyncListDiffer to handle the list updates asynchronously
    val differ = AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritesMealsAdapterViewHolder {
        // Create a ViewHolder by inflating the item layout using MealItemBinding
        return FavoritesMealsAdapterViewHolder(
            MealItemBinding.inflate(
                LayoutInflater.from(parent.context), parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: FavoritesMealsAdapterViewHolder, position: Int) {

        // Get the meal at the current position
        val meal = differ.currentList[position]

        // Load the meal image using Glide
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imgMeal)

        // Set the meal name
        holder.binding.tvMealName.text = meal.strMeal



    }

    override fun getItemCount(): Int {
        // Return the number of items in the list
        return differ.currentList.size
    }

     // ViewHolder class for holding the item views
    inner class FavoritesMealsAdapterViewHolder(val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)
}