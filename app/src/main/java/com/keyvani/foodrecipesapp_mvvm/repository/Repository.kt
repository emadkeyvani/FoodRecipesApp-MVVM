package com.keyvani.foodrecipesapp_mvvm.repository

import com.keyvani.foodrecipesapp_mvvm.data.LocalDataSource
import com.keyvani.foodrecipesapp_mvvm.data.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
@ActivityRetainedScoped
class Repository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    val remote = remoteDataSource
    val local = localDataSource
}