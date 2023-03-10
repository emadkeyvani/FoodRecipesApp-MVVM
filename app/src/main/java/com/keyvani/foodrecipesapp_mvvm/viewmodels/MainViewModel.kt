package com.keyvani.foodrecipesapp_mvvm.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.keyvani.foodrecipesapp_mvvm.db.entities.FavoritesEntity
import com.keyvani.foodrecipesapp_mvvm.db.entities.FoodJokeEntity
import com.keyvani.foodrecipesapp_mvvm.db.entities.RecipesEntity
import com.keyvani.foodrecipesapp_mvvm.responses.FoodRecipe
import com.keyvani.foodrecipesapp_mvvm.repository.Repository
import com.keyvani.foodrecipesapp_mvvm.responses.FoodJoke
import com.keyvani.foodrecipesapp_mvvm.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** ROOM DATABASE*/
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()
    val readFavoriteRecipes: LiveData<List<FavoritesEntity>> = repository.local.readFavoriteRecipes().asLiveData()
    val readFoodJoke: LiveData<List<FoodJokeEntity>> = repository.local.readFoodJoke().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavoriteRecipes(favoritesEntity)
        }

    private fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFoodJoke(foodJokeEntity)
        }

    fun deleteFavoriteRecipes(favoritesEntity: FavoritesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavoriteRecipe(favoritesEntity)
        }

    fun deleteAllFavoriteRecipes() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteAllFavoriteRecipes()
        }


    /** RETROFIT*/
    private val _recipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val recipesResponse: LiveData<NetworkResult<FoodRecipe>>
        get() = _recipesResponse

    private val _searchRecipesResponse: MutableLiveData<NetworkResult<FoodRecipe>> = MutableLiveData()
    val searchRecipesResponse: LiveData<NetworkResult<FoodRecipe>>
        get() = _searchRecipesResponse

    private val _foodJokeResponse: MutableLiveData<NetworkResult<FoodJoke>> = MutableLiveData()
    val foodJokeResponse: LiveData<NetworkResult<FoodJoke>>
        get() = _foodJokeResponse

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        searchRecipesSafeCall(searchQuery)
    }

    fun getFoodJoke(apiKey: String) = viewModelScope.launch {
        getFoodJokeSafeCall(apiKey)
    }


    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        _recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getRecipes(queries)
                _recipesResponse.value = handleFoodRecipesResponse(response)

                //Insert to database
                val foodRecipe = _recipesResponse.value!!.data
                if (foodRecipe != null) {
                    offlineCashRecipes(foodRecipe)
                }

            } catch (_: Exception) {
                _recipesResponse.value = NetworkResult.Error("Recipes not found")

            }
        } else {
            _recipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }

    }

    private suspend fun searchRecipesSafeCall(searchQuery: Map<String, String>) {
        _searchRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.searchRecipes(searchQuery)
                _searchRecipesResponse.value = handleFoodRecipesResponse(response)

            } catch (_: Exception) {
                _searchRecipesResponse.value = NetworkResult.Error("Recipes not found")

            }
        } else {
            _searchRecipesResponse.value = NetworkResult.Error("No Internet Connection.")
        }

    }

    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        _foodJokeResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getFoodJoke(apiKey)
                _foodJokeResponse.value = handleFoodJokeResponse(response)

                val foodJoke = foodJokeResponse.value!!.data
                if (foodJoke !=null){
                    offlineCashFoodJoke(foodJoke)
                }
            } catch (_: Exception) {
                _foodJokeResponse.value = NetworkResult.Error("Recipes not found")

            }
        } else {
            _foodJokeResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    //Insert to database
    private fun offlineCashRecipes(foodRecipe: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)

    }

    private fun offlineCashFoodJoke(foodJoke: FoodJoke) {
        val foodJokeEntity = FoodJokeEntity(foodJoke)
        insertFoodJoke(foodJokeEntity)

    }

    private fun handleFoodRecipesResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe> {

        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API key Limited")
            }
            response.body()!!.results.isEmpty() -> {
                return NetworkResult.Error("Recipes not found")
            }
            response.isSuccessful -> {
                val foodRecipe = response.body()
                return NetworkResult.Success(foodRecipe!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke> {

        return when {
            response.message().toString().contains("timeout") -> {
                 NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                 NetworkResult.Error("API key Limited")
            }

            response.isSuccessful -> {
                val foodJoke = response.body()
                 NetworkResult.Success(foodJoke!!)
            }
            else -> {
                 NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}