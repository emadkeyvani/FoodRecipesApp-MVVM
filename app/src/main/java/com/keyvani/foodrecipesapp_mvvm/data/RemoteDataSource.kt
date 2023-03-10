package com.keyvani.foodrecipesapp_mvvm.data

import com.keyvani.foodrecipesapp_mvvm.responses.FoodJoke
import com.keyvani.foodrecipesapp_mvvm.api.ApiServices
import com.keyvani.foodrecipesapp_mvvm.responses.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiServices: ApiServices
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return apiServices.getRecipes(queries)
    }

    suspend fun searchRecipes(searchQuery: Map<String, String>): Response<FoodRecipe> {
        return apiServices.searchRecipes(searchQuery)
    }

    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke> {
        return apiServices.getFoodJoke(apiKey)
    }
}