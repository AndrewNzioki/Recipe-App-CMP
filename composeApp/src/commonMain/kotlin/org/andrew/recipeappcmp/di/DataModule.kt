package org.andrew.recipeappcmp.di

import org.andrew.recipeappcmp.features.detail.data.datasource.RecipeDetailLocalDataSource
import org.andrew.recipeappcmp.features.detail.data.datasource.RecipeDetailLocalDataSourceImpl
import org.andrew.recipeappcmp.features.detail.data.datasource.RecipeDetailRemoteDataSource
import org.andrew.recipeappcmp.features.detail.data.datasource.RecipeDetailRemoteDataSourceImpl
import org.andrew.recipeappcmp.features.detail.repositories.RecipeDetailRepository
import org.andrew.recipeappcmp.features.detail.repositories.RecipeDetailRepositoryImpl
import org.andrew.recipeappcmp.features.favorites.data.FavoriteRecipeLocalDataSource
import org.andrew.recipeappcmp.features.favorites.data.FavoriteRecipeLocalDataSourceImpl
import org.andrew.recipeappcmp.features.favorites.domain.FavoriteRecipeRepository
import org.andrew.recipeappcmp.features.favorites.domain.FavoriteRecipeRepositoryImpl
import org.andrew.recipeappcmp.features.feed.data.datasources.FeedLocalDataSource
import org.andrew.recipeappcmp.features.feed.data.datasources.FeedLocalDataSourceImpl
import org.andrew.recipeappcmp.features.feed.data.datasources.FeedRemoteDataSource
import org.andrew.recipeappcmp.features.feed.data.datasources.FeedRemoteDataSourceImpl
import org.andrew.recipeappcmp.features.feed.data.repositories.FeedRepositoryImpl
import org.andrew.recipeappcmp.features.feed.domain.repositories.FeedRepository
import org.andrew.recipeappcmp.preferences.AppPreferences
import org.andrew.recipeappcmp.preferences.AppPreferencesImpl
import org.koin.dsl.module

fun dataModule() = module {
    single<FeedLocalDataSource>{ FeedLocalDataSourceImpl(get()) }

    single<FeedRemoteDataSource> { FeedRemoteDataSourceImpl(get()) }

    single<RecipeDetailLocalDataSource> { RecipeDetailLocalDataSourceImpl(get(), get()) }

    single<RecipeDetailRemoteDataSource> { RecipeDetailRemoteDataSourceImpl(get()) }

    single<FeedRepository> { FeedRepositoryImpl(get(), get()) }

    single<RecipeDetailRepository> { RecipeDetailRepositoryImpl(get(), get()) }

    //Favorite feature
    single<FavoriteRecipeLocalDataSource> { FavoriteRecipeLocalDataSourceImpl(get()) }
    single<FavoriteRecipeRepository> { FavoriteRecipeRepositoryImpl(get()) }

    //AppPreference
    single<AppPreferences>{ AppPreferencesImpl(get()) }
}