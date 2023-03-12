package com.keyvani.foodrecipesapp_mvvm.utils

import com.keyvani.foodrecipesapp_mvvm.BuildConfig


object Constants {

    const val BASE_URL = "https://api.spoonacular.com/"
    const val NETWORK_TIMEOUT = 60L
    const val BASE_IMAGE_URL ="https://spoonacular.com/cdn/ingredients_100x100/"

    const val API_KEY: String = BuildConfig.API_KEY
    const val QUERY_NUMBER = "number"
    const val QUERY_API_KEY = "apiKey"
    const val QUERY_TYPE = "type"
    const val QUERY_DIET = "diet"
    const val QUERY_INFO = "addRecipeInformation"
    const val QUERY_INGREDIENTS = "fillIngredients"
    const val QUERY_SEARCH = "query"


    const val DATABASE_NAME = "recipes_database"
    const val RECIPES_TABLE = "recipes_table"
    const val FAVORITE_RECIPES_TABLE = "favorite_recipes_table"
    const val FOOD_JOKE_TABLE = "food_joke_table"

    const val DEFAULT_MEAL_TYPE = "main course"
    const val DEFAULT_DIET_TYPE = "gluten free"
    const val DEFAULT_RECIPES_NUMBER = "50"

    const val PREFERENCES_NAME = "foody_preferences"
    const val PREFERENCES_MEAL_TYPE = "mealType"
    const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
    const val PREFERENCES_DIET_TYPE = "dietType"
    const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
    const val PREFERENCES_BACK_ONLINE = "backOnline"

    const val RECIPE_RESULT_KEY = "recipeBundle"


}