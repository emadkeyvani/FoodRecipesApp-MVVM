package com.keyvani.foodrecipesapp_mvvm.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.keyvani.foodrecipesapp_mvvm.responses.Result
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.FAVORITE_RECIPES_TABLE


@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
) {
}