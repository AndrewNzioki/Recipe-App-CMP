package org.andrew.recipeappcmp.features.favorites.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem
import org.andrew.recipeappcmp.features.common.ui.components.ErrorContent
import org.andrew.recipeappcmp.features.common.ui.components.Loader
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun FavoritesRoute(
    favoritesScreenViewModel: FavoriteScreenViewModel = koinViewModel(),
    navigateToDetail: (Long) -> Unit
){

    val uiState = favoritesScreenViewModel.favoriteScreenUiState.collectAsStateWithLifecycle()


    FavoritesScreen(
        uiState = uiState.value,
        navigateToDetail = navigateToDetail
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    uiState: FavoriteScreenUiState,
    navigateToDetail: (Long) -> Unit
){
    val recipes = uiState.itemsList
    Scaffold(
        modifier = Modifier.systemBarsPadding(),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                title ={
                    Text(
                        "Favorites"
                    )
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding())
        ) {
            HorizontalDivider(
                thickness = 0.3.dp,
                color = MaterialTheme.colorScheme.outline.copy(
                    alpha = 0.5f
                )
            )
            when{
                uiState.itemsListIsLoading -> {
                    Loader()
                }

                uiState.itemsListError != null -> {
                    ErrorContent(uiState.itemsListError)
                }

                recipes != null -> {
                    FavoriteContent(
                        innerPadding = innerPadding,
                        recipes = recipes,
                        navigateToDetail = navigateToDetail
                    )
                }
            }
        }


    }
}

@Composable
fun FavoriteContent(
    innerPadding: PaddingValues,
    recipes: List<RecipeItem>,
    navigateToDetail: (Long) -> Unit
){
    val imageModifier = Modifier
        .width(140.dp)
        .height(80.dp)
        .clip(RoundedCornerShape(16.dp))

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(recipes, key = {
            it.id
        }){
            FavoriteRecipeCard(
                recipe = it,
                imageModifier = imageModifier,
                navigateToDetail = navigateToDetail
            )
        }
    }
}

@Composable
private fun FavoriteRecipeCard(
    recipe: RecipeItem,
    modifier: Modifier = Modifier,
    imageModifier: Modifier,
    navigateToDetail: (Long) -> Unit
){
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier,
        onClick = {navigateToDetail(recipe.id)}
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {


            AsyncImage(
                model = recipe.imageUrl,
                onError = {
                    println("AsyncImage_onError${it.result.throwable}}")
                },
                modifier = imageModifier,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Column {


                Text(
                    textAlign = TextAlign.Start,
                    text = recipe.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = recipe.duration,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${recipe.rating}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                }
            }
        }
    }
}