package com.keyvani.foodrecipesapp_mvvm.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.keyvani.foodrecipesapp_mvvm.db.entities.RecipesEntity
import com.keyvani.foodrecipesapp_mvvm.responses.FoodRecipe
import com.keyvani.foodrecipesapp_mvvm.utils.NetworkResult

class RecipesBinding {

    companion object {
        @BindingAdapter("readApiResponse","readDatabase", requireAll = true)
        @JvmStatic
        fun handleReadDataErrors(
            view: View,
            apiResponse: NetworkResult<FoodRecipe>?,
            database: List<RecipesEntity>?
        ) {
            when(view){
                is ImageView->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                }
                is TextView->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                    view.text = apiResponse?.message.toString()
                }
            }
        }
    }
}