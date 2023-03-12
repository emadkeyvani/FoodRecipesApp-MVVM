package com.keyvani.foodrecipesapp_mvvm.db.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.keyvani.foodrecipesapp_mvvm.responses.FoodJoke
import com.keyvani.foodrecipesapp_mvvm.utils.Constants.FOOD_JOKE_TABLE

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}