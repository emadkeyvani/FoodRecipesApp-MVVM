package com.keyvani.foodrecipesapp_mvvm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.keyvani.foodrecipesapp_mvvm.db.entities.FavoritesEntity
import com.keyvani.foodrecipesapp_mvvm.db.entities.FoodJokeEntity
import com.keyvani.foodrecipesapp_mvvm.db.entities.RecipesEntity

@Database(
    entities = [RecipesEntity::class , FavoritesEntity::class , FoodJokeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipesDao(): RecipesDao

}