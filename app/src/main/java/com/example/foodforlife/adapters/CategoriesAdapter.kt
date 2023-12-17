package com.example.foodforlife.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodforlife.databinding.CategoryItemBinding
import com.example.foodforlife.pojo.Category
import kotlin.collections.ArrayList

class CategoriesAdapter(): RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder>() {


    private var categoriesList = ArrayList<Category>()
    var onItemClick : ((Category) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setCategoriesList(categoriesList: ArrayList<Category>){
        this.categoriesList = categoriesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            CategoryItemBinding.inflate(
                LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(categoriesList[position].strCategoryThumb)
            .into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text = categoriesList[position].strCategory

        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoriesList[position])
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }

    inner class CategoryViewHolder(val binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root)
}