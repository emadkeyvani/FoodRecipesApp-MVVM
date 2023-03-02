package com.keyvani.foodrecipesapp_mvvm.viewmodels

import androidx.lifecycle.ViewModel
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.API_KEY
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.QUERY_API_KEY
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.QUERY_DIET
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.QUERY_INFO
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.QUERY_INGREDIENTS
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.QUERY_NUMBER
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(): ViewModel(){

     fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "Vegan"
        queries[QUERY_INFO] = "true"
        queries[QUERY_INGREDIENTS] = "true"
        return queries
    }
}