package com.keyvani.foodrecipesapp_mvvm.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.keyvani.foodrecipesapp_mvvm.repository.DataStoreRepository
import com.keyvani.foodrecipesapp_mvvm.repository.MealAndDietType
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.API_KEY
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.DEFAULT_DIET_TYPE
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.DEFAULT_MEAL_TYPE
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.DEFAULT_RECIPES_NUMBER
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.QUERY_API_KEY
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.QUERY_DIET
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.QUERY_INFO
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.QUERY_INGREDIENTS
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.QUERY_NUMBER
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.QUERY_SEARCH
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application, private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {


    private lateinit var mealAndDiet: MealAndDietType
    var networkStatus = false
    var backOnline = false
    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun saveMealAndDietType() =
        viewModelScope.launch(Dispatchers.IO) {
            if (this@RecipesViewModel::mealAndDiet.isInitialized) {
                dataStoreRepository.saveMealAndDietType(
                    mealAndDiet.selectedMealType,
                    mealAndDiet.selectedMealTypeId,
                    mealAndDiet.selectedDietType,
                    mealAndDiet.selectedDietTypeId
                )
            }
        }

    fun saveMealAndDietTypeTemp(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) {
        mealAndDiet = MealAndDietType(
            mealType,
            mealTypeId,
            dietType,
            dietTypeId
        )
    }
    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_INFO] = "true"
        queries[QUERY_INGREDIENTS] = "true"

        if (this@RecipesViewModel::mealAndDiet.isInitialized) {
            queries[QUERY_TYPE] = mealAndDiet.selectedMealType
            queries[QUERY_DIET] = mealAndDiet.selectedDietType
        } else {
            queries[QUERY_TYPE] = DEFAULT_MEAL_TYPE
            queries[QUERY_DIET] = DEFAULT_DIET_TYPE
        }

        return queries
    }
    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_INFO] = "true"
        queries[QUERY_INGREDIENTS] = "true"
        return queries
    }

     private fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }
    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection.", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "We're back online.", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }
}