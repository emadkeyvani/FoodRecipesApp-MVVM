package com.keyvani.foodrecipesapp_mvvm.responses

import com.google.gson.annotations.SerializedName

data class FoodRecipe(
    @SerializedName("results")
    val results: List<Result>
)
