package com.keyvani.foodrecipesapp_mvvm.utils

import com.keyvani.foodrecipesapp_mvvm.BuildConfig


object Constants {

    const val BASE_URL = "https://api.spoonacular.com/"
    const val NETWORK_TIMEOUT = 60L

    const val API_KEY: String = BuildConfig.API_KEY
    const val QUERY_NUMBER = "number"
    const val QUERY_API_KEY = "apiKey"
    const val QUERY_TYPE = "type"
    const val QUERY_DIET = "diet"
    const val QUERY_INFO = "addRecipeInformation"
    const val QUERY_INGREDIENTS = "fillIngredients"


    const val DATABASE_NAME = "recipes_database"
    const val RECIPES_TABLE = "recipes_table"
}