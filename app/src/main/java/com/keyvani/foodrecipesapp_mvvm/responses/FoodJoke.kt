package com.keyvani.foodrecipesapp_mvvm.responses


import com.google.gson.annotations.SerializedName

data class FoodJoke(
    @SerializedName("text")
    val text: String
)