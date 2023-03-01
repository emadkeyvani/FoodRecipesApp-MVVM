package com.keyvani.foodrecipesapp_mvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.keyvani.foodrecipesapp_mvvm.databinding.ItemRowBinding
import com.keyvani.foodrecipesapp_mvvm.models.FoodRecipe
import com.keyvani.foodrecipesapp_mvvm.models.Result
import com.keyvani.foodrecipesapp_mvvm.utils.RecipesDiffUtils
import javax.inject.Inject

class RecipesAdapter @Inject constructor() : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {
    private var recipes = emptyList<Result>()

    class MyViewHolder(private val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result) {
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRowBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    fun setData(newData:FoodRecipe){
        val recipeDiffUtil = RecipesDiffUtils(recipes,newData.results)
        val diffUtils = DiffUtil.calculateDiff(recipeDiffUtil)
        recipes = newData.results
        diffUtils.dispatchUpdatesTo(this)
    }

}