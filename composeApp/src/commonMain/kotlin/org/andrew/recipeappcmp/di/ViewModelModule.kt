package org.andrew.recipeappcmp.di


import org.andrew.recipeappcmp.features.detail.ui.RecipeDetailViewModel
import org.andrew.recipeappcmp.features.favorites.ui.FavoriteScreenViewModel
import org.andrew.recipeappcmp.features.feed.ui.FeedViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun viewModelModule() = module {
    viewModel {
        FeedViewModel(get())
    }
    viewModel {
        RecipeDetailViewModel(get(), get())
    }
    viewModel {
        FavoriteScreenViewModel(get())
    }
}