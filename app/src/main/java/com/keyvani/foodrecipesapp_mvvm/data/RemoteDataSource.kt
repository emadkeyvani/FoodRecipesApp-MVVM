package com.keyvani.foodrecipesapp_mvvm.data

import com.keyvani.foodrecipesapp_mvvm.api.ApiServices
import com.keyvani.foodrecipesapp_mvvm.models.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiServices: ApiServices
) {

    suspend fun getRecipes(queries:Map<String,String>): Response<FoodRecipe> {
        return apiServices.getRecipes(queries)
    }
}