package org.andrew.recipeappcmp.features.detail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.andrew.recipeappcmp.features.common.data.models.capitalizeFirstWord
import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun DetailRoute(
    recipeId: Long,
    onBackClick: () -> Unit,
    detailViewModel: RecipeDetailViewModel = koinViewModel()
) {

    LaunchedEffect(Unit) {
        detailViewModel.getRecipeDetail(recipeId)
    }

    val detailUiState = detailViewModel.detailUiState.collectAsStateWithLifecycle()

    val uriHandler = LocalUriHandler.current

    val onWatchVideoClick: (String) -> Unit = { link ->
        if(link.isNotEmpty()){
            uriHandler.openUri(link)
        }
    }

    val onSaveClick: (RecipeItem) -> Unit = {
        detailViewModel.updateIsFavorite(recipeId = it.id, isAdding = !it.isFavorite)
    }

    val updateIsFavoriteUiState = detailViewModel.updateIsFavoriteUiState.collectAsStateWithLifecycle()

    DetailScreen(
        uiState = detailUiState.value,
        onBackClick = onBackClick,
        onWatchVideoClick = onWatchVideoClick,
        onSaveClick = onSaveClick,
        updateIsFavoriteUiState = updateIsFavoriteUiState.value
    )
}


@Composable
fun DetailScreen(
    uiState: RecipeDetailUiState,
    onBackClick: () -> Unit,
    onWatchVideoClick: (String) -> Unit,
    updateIsFavoriteUiState: RecipeDetailUpdateIsFavoriteUiState,
    onSaveClick: (RecipeItem) -> Unit
) {
    Scaffold(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
        ) {
            when {
                uiState.recipesDetailIsLoading -> {
                    LoadingScreen()
                }

                uiState.recipesDetailError != null -> {
                    ErrorScreen(uiState.recipesDetailError, onBackClick)
                }

                uiState.recipesDetail != null -> {
                    RecipeDetailContent(
                        recipeItem = uiState.recipesDetail,
                        onBackClick = onBackClick,
                        onWatchVideoClick = onWatchVideoClick,
                        onSaveClick = onSaveClick
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    errorMessage: String, onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onBackClick
        ) {
            Text("Go Back")
        }
    }
}

@Composable
fun RecipeDetailContent(
    recipeItem: RecipeItem,
    onBackClick: () -> Unit,
    onWatchVideoClick: (String) -> Unit,
    onSaveClick: (RecipeItem) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        RecipeMainContent(
            recipeItem = recipeItem,
            onWatchVideoClick = onWatchVideoClick

        )

        //Back and Save Button UI
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(WindowInsets.statusBars.asPaddingValues())
                .padding(vertical = 32.dp).padding(horizontal = 16.dp).align(Alignment.TopCenter)
        ) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.padding(horizontal = 8.dp).size(30.dp)
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            IconButton(
                onClick = { onSaveClick(recipeItem) },
                modifier = Modifier.padding(horizontal = 8.dp).size(30.dp)
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
            ) {
                Icon(
                    imageVector = if (recipeItem.isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

        }
    }
}

@Composable
fun RecipeMainContent(
    recipeItem: RecipeItem,
    onWatchVideoClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {

        //Image
        AsyncImage(
            model = recipeItem.imageUrl,
            contentDescription = recipeItem.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(250.dp).clip(
                    RoundedCornerShape(
                        bottomStart = 16.dp, bottomEnd = 16.dp
                    )
                )
        )

        //Other Details
        RecipeDetails(recipeItem)

        //Description
        Column(
            modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 16.dp)
        ) {
            Text(
                text = "Description", style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                text = recipeItem.description, style = MaterialTheme.typography.bodySmall
            )
        }

        //Ingredients
        IngredientsList(recipeItem.ingredients.map {
                val item = it.split(":")
                if (item.isNotEmpty() && item.size == 2) {
                    Pair(item[0].trim().capitalizeFirstWord(), item[1].trim())
                } else {
                    Pair("", "")
                }
            }.filter {
                it.first.isNotEmpty() && it.second.isNotEmpty()
            }.filterNot {
                it.first.contains("null") || it.second.contains("null")
            })

        //Instructions
        Instructions(recipeItem.instructions)

        //Watch video button
        WatchVideoButton(
            youtubeLink = recipeItem.youtubeLink,
            onWatchVideoClick = onWatchVideoClick
        )
    }
}

@Composable
fun RecipeDetails(
    recipeItem: RecipeItem
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = recipeItem.title, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Row(
            modifier = Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp), contentDescription = null, imageVector = Icons.Default.Schedule
            )

            Text(
                text = recipeItem.duration,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                modifier = Modifier.size(16.dp),
                contentDescription = null,
                imageVector = Icons.Default.Star,
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
            Text(
                text = "${recipeItem.rating}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = recipeItem.difficulty,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

@Composable
fun IngredientsList(
    ingredients: List<Pair<String, String>>
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Ingredients", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        ingredients.forEach {
            IngredientsItem(
                name = it.first, quantity = it.second
            )
        }
    }
}

@Composable
fun IngredientsItem(
    name: String, quantity: String
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = name, style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = quantity, style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun Instructions(
    instructions: List<String>
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Instructions", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )

        instructions.forEachIndexed { index, string ->
            Text(
                text = "${index + 1} $string",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun WatchVideoButton(
    youtubeLink: String,
    onWatchVideoClick: (String) -> Unit
){
    Button(
        onClick = {
            onWatchVideoClick(youtubeLink)
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ){
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Watch",
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            "Watch Video",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
        )
    }
}
