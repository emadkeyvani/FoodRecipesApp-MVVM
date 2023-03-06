package com.keyvani.foodrecipesapp_mvvm.bindingadapters

import android.util.Log
import android.view.View
import android.widget.ImageView
import com.keyvani.foodrecipesapp_mvvm.responses.Result
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.keyvani.foodrecipesapp_mvvm.R
import com.keyvani.foodrecipesapp_mvvm.ui.fragments.RecipesFragmentDirections


class RecipesRowBinding {
    companion object{

        @BindingAdapter("onRecipeClickListener")
        @JvmStatic
        fun onRecipeClickListener(recipeRowLayout: ConstraintLayout, result:Result ) {
            Log.d("onRecipeClickListener", "CALLED")
            recipeRowLayout.setOnClickListener {
                try {
                    val action =
                        RecipesFragmentDirections.actionRecipesFragmentToDetailsActivity(result)
                    recipeRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onRecipeClickListener", e.toString())
                }
            }
        }
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