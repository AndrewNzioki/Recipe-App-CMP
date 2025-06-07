package org.andrew.recipeappcmp.features.common.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem

/**
 * Represents a recipe item as returned by the MealDB API.
 * This model is used for decoding the JSON response from the API.
 */
@Serializable
data class RecipeApiItem(
    /** Last modified date of the recipe. */
    @SerialName("dateModified") val dateModified: String?,
    /** Unique ID of the meal. */
    @SerialName("idMeal") val idMeal: String?,
    /** Geographic area or cuisine of the recipe (e.g., Italian, Mexican). */
    @SerialName("strArea") val strArea: String?,
    /** Category of the meal (e.g., Dessert, Seafood). */
    @SerialName("strCategory") val strCategory: String?,
    /** Creative Commons confirmation status. */
    @SerialName("strCreativeCommonsConfirmed") val strCreativeCommonsConfirmed: String?,
    /** Source URL of the image. */
    @SerialName("strImageSource") val strImageSource: String?,
    // Ingredients 1-20
    @SerialName("strIngredient1") val strIngredient1: String?,
    @SerialName("strIngredient2") val strIngredient2: String?,
    @SerialName("strIngredient3") val strIngredient3: String?,
    @SerialName("strIngredient4") val strIngredient4: String?,
    @SerialName("strIngredient5") val strIngredient5: String?,
    @SerialName("strIngredient6") val strIngredient6: String?,
    @SerialName("strIngredient7") val strIngredient7: String?,
    @SerialName("strIngredient8") val strIngredient8: String?,
    @SerialName("strIngredient9") val strIngredient9: String?,
    @SerialName("strIngredient10") val strIngredient10: String?,
    @SerialName("strIngredient11") val strIngredient11: String?,
    @SerialName("strIngredient12") val strIngredient12: String?,
    @SerialName("strIngredient13") val strIngredient13: String?,
    @SerialName("strIngredient14") val strIngredient14: String?,
    @SerialName("strIngredient15") val strIngredient15: String?,
    @SerialName("strIngredient16") val strIngredient16: String?,
    @SerialName("strIngredient17") val strIngredient17: String?,
    @SerialName("strIngredient18") val strIngredient18: String?,
    @SerialName("strIngredient19") val strIngredient19: String?,
    @SerialName("strIngredient20") val strIngredient20: String?,
    // Instructions
    /** Full recipe instructions as a single string. */
    @SerialName("strInstructions") val strInstructions: String?,
    /** Title/name of the meal. */
    @SerialName("strMeal") val strMeal: String?,
    /** Alternate name of the meal, if available. */
    @SerialName("strMealAlternate") val strMealAlternate: String?,
    /** Thumbnail image URL of the meal. */
    @SerialName("strMealThumb") val strMealThumb: String?,
    // Measures 1-20
    @SerialName("strMeasure1") val strMeasure1: String?,
    @SerialName("strMeasure2") val strMeasure2: String?,
    @SerialName("strMeasure3") val strMeasure3: String?,
    @SerialName("strMeasure4") val strMeasure4: String?,
    @SerialName("strMeasure5") val strMeasure5: String?,
    @SerialName("strMeasure6") val strMeasure6: String?,
    @SerialName("strMeasure7") val strMeasure7: String?,
    @SerialName("strMeasure8") val strMeasure8: String?,
    @SerialName("strMeasure9") val strMeasure9: String?,
    @SerialName("strMeasure10") val strMeasure10: String?,
    @SerialName("strMeasure11") val strMeasure11: String?,
    @SerialName("strMeasure12") val strMeasure12: String?,
    @SerialName("strMeasure13") val strMeasure13: String?,
    @SerialName("strMeasure14") val strMeasure14: String?,
    @SerialName("strMeasure15") val strMeasure15: String?,
    @SerialName("strMeasure16") val strMeasure16: String?,
    @SerialName("strMeasure17") val strMeasure17: String?,
    @SerialName("strMeasure18") val strMeasure18: String?,
    @SerialName("strMeasure19") val strMeasure19: String?,
    @SerialName("strMeasure20") val strMeasure20: String?,
    /** Source URL for the meal. */
    @SerialName("strSource") val strSource: String?,
    /** Tags associated with the meal. */
    @SerialName("strTags") val strTags: String?,
    /** YouTube link to a video tutorial for the recipe. */
    @SerialName("strYoutube") val strYoutube: String?
)

/**
 * Converts a [RecipeApiItem] into a domain-level [RecipeItem].
 *
 * @return A [RecipeItem] object if `idMeal` is not null, otherwise `null`.
 */
fun RecipeApiItem.toRecipe(): RecipeItem? {
    return if (idMeal != null) {
        RecipeItem(
            id = idMeal.toLong(),
            title = strMeal ?: "",
            description = strInstructions ?: "",
            category = strCategory ?: "",
            area = strArea ?: "",
            imageUrl = strMealThumb ?: "",
            youtubeLink = strYoutube ?: "",
            ingredients = listOfNotNull(
                strIngredient1?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure1.orEmpty()}" },
                strIngredient2?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure2.orEmpty()}" },
                strIngredient3?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure3.orEmpty()}" },
                strIngredient4?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure4.orEmpty()}" },
                strIngredient5?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure5.orEmpty()}" },
                strIngredient6?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure6.orEmpty()}" },
                strIngredient7?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure7.orEmpty()}" },
                strIngredient8?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure8.orEmpty()}" },
                strIngredient9?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure9.orEmpty()}" },
                strIngredient10?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure10.orEmpty()}" },
                strIngredient11?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure11.orEmpty()}" },
                strIngredient12?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure12.orEmpty()}" },
                strIngredient13?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure13.orEmpty()}" },
                strIngredient14?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure14.orEmpty()}" },
                strIngredient15?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure15.orEmpty()}" },
                strIngredient16?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure16.orEmpty()}" },
                strIngredient17?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure17.orEmpty()}" },
                strIngredient18?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure18.orEmpty()}" },
                strIngredient19?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure19.orEmpty()}" },
                strIngredient20?.takeIf { it.isNotBlank() }?.let { "$it: ${strMeasure20.orEmpty()}" }
            ),
            instructions = strInstructions
                ?.split(Regex("\\r?\\n"))
                ?.map { it.trim().capitalizeFirstWord() }
                ?.filter { it.isNotEmpty() }
                ?: emptyList(),
            isFavorite = false,
            rating = 3L
        )
    } else null
}

/**
 * Extension function which capitalizes the first character of a string.
 */
fun String.capitalizeFirstWord() = this.replaceFirstChar { it.uppercase() }
