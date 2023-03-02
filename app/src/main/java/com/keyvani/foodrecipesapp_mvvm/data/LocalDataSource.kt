package com.keyvani.foodrecipesapp_mvvm.data

import com.keyvani.foodrecipesapp_mvvm.db.RecipesDao
import com.keyvani.foodrecipesapp_mvvm.db.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {

    fun readDatabase(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }
}