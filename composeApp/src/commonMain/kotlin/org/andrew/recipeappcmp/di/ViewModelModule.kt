package org.andrew.recipeappcmp.di


import org.andrew.recipeappcmp.features.detail.ui.RecipeDetailViewModel
import org.andrew.recipeappcmp.features.favorites.ui.FavoriteScreenViewModel
import org.andrew.recipeappcmp.features.feed.ui.FeedViewModel
import org.andrew.recipeappcmp.features.login.ui.LoginViewModel
import org.andrew.recipeappcmp.features.profile.ui.ProfileViewModel
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
    viewModel {
        ProfileViewModel()
    }

    viewModel{
        LoginViewModel()
    }
}