package com.keyvani.foodrecipesapp_mvvm.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.keyvani.foodrecipesapp_mvvm.models.FoodRecipe
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}