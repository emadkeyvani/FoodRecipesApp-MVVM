package com.keyvani.foodrecipesapp_mvvm.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import coil.load
import com.keyvani.foodrecipesapp_mvvm.R
import com.keyvani.foodrecipesapp_mvvm.models.FoodRecipe
import com.keyvani.foodrecipesapp_mvvm.utils.NetworkResult


class RecipesRowBinding {
    companion object{
        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView , imageUrl : String){
            imageView.load(imageUrl){
                crossfade(800)
                crossfade(true)
                error(R.drawable.ic_error_placeholder)
            }
        }

        @BindingAdapter("applyVeganColor")
        @JvmStatic
        fun applyVeganColor(view: View, vegan: Boolean) {
            if(vegan){
                when(view){
                    is TextView -> {
                        view.setTextColor(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            }
        }


    }
}