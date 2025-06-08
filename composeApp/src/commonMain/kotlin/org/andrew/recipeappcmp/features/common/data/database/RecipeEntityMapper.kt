package org.andrew.recipeappcmp.features.common.data.database

import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem
import organdrewrecipeappcmp.Recipe

fun recipeEntityMapper(recipe: Recipe) = RecipeItem(
    recipe.id,
    recipe.title,
    recipe.description,
    recipe.category,
    recipe.area,
    recipe.imageUrl,
    recipe.youtubeLink,
    recipe.ingredients,
    recipe.instructions,
    recipe.isFavorite == 1L,
    recipe.rating,
    recipe.duration ?: "20 mins",
    recipe.difficulty ?: "Easy"
)