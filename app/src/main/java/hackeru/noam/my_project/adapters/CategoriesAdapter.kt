package hackeru.noam.my_project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hackeru.noam.my_project.databinding.CategoryItemBinding
import hackeru.noam.my_project.pojo.Category

class CategoriesAdapter():RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder> (){

    private var categoriesList = ArrayList<Category>()
    var onItemClick:((Category)-> Unit)? = null

    // Set the category list
    fun setCategoryList(categoriesList:List<Category>){
        this.categoriesList = categoriesList as ArrayList<Category>
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        // Create a ViewHolder by inflating the item layout using CategoryItemBinding
        return CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        // Load the category image using Glide
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        // Set the category name
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory

        // Set click listener for the item view
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int {
        // Return the number of items in the list
        return categoriesList.size
    }
}