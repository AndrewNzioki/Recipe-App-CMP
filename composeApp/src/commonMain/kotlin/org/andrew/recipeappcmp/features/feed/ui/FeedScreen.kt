package org.andrew.recipeappcmp.features.feed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FeedRoute(
    navigateToSearch: () -> Unit,
    feedViewModel: FeedViewModel = koinViewModel()
){
    val feedUiState = feedViewModel.feedUiState.collectAsStateWithLifecycle()

    FeedScreen(
        feedUiState = feedUiState.value,
        navigateToSearch = navigateToSearch
    )
}


@Composable
fun FeedScreen(
    feedUiState: FeedUiState,
    navigateToSearch: () -> Unit
){
    val recipes = feedUiState.recipesList
    Scaffold(
        topBar = {TopBar()}
    ) { innerPadding ->

        when{
            feedUiState.recipesListIsLoading -> {
                Loader()
            }

            feedUiState.recipesListError != null -> {
                ErrorContent(feedUiState.recipesListError)
            }

            recipes != null -> {
                FeedContent(innerPadding, recipes)
            }
        }

    }
}

@Composable
private fun Loader(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primaryContainer
        )
    }
}

@Composable
private fun ErrorContent(errorMessage: String){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        Text(
            text = "Error in Loading Items: $errorMessage",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.error
            )
            )
    }
}

@Composable
private fun FeedContent(
    innerPadding: PaddingValues,
    recipes: List<RecipeItem>
){
    Column(

        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding()
            )
        ){
          item(
              span = { GridItemSpan(maxLineSpan) }
          ) {
             TopRecipesList(
                 title = "Top Recommendations",
                 recipes = recipes.reversed()
             )
          }

            recipesOfTheWeek(
                title = "Recipes Of the Week",
                recipes = recipes
            )
        }


    }
}

@Composable
private fun TopRecipesList(
    title: String,
    recipes: List<RecipeItem>
){
    Column{
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(recipes, key = { it.id }){ recipe ->
                val imageModifier = Modifier
                    .width(120.dp)
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                RecipeCard(
                    recipe = recipe,
                    modifier = Modifier.width(120.dp),
                    imageModifier = imageModifier
                )
            }
        }
    }
}


private fun LazyGridScope.recipesOfTheWeek(
    title: String,
    recipes: List<RecipeItem>
){
    item(
        span = { GridItemSpan(maxLineSpan) }
    ){
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(top = 16.dp, start = 16.dp)
        )
    }

    itemsIndexed(recipes, key = {  _, it -> it.id }){index, recipe ->

        val cardPaddingStart = if(index % 2 == 0) 16.dp else 0.dp
        val cardPaddingEnd = if(index % 2 != 0) 16.dp else 0.dp
        val imageModifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clip(RoundedCornerShape(16.dp))
        RecipeCard(
            recipe = recipe,
            modifier = Modifier
                .padding(start = cardPaddingStart, end = cardPaddingEnd)
            ,
            imageModifier = imageModifier
        )
    }
}

@Composable
private fun RecipeCard(
    recipe: RecipeItem,
    modifier: Modifier,
    imageModifier: Modifier
){
   Column(
       horizontalAlignment = Alignment.CenterHorizontally,
       modifier = modifier
   ){
       AsyncImage(
           model = recipe.imageUrl,
           onError = {
               println("AsyncImage_onError${it.result.throwable}}")
           },
           modifier = imageModifier,
           contentScale = ContentScale.Crop,
           contentDescription = null
       )

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
       ){
           Row(
               verticalAlignment = Alignment.CenterVertically
           ){
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
           ){
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

@Composable
private fun SearchBar(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart,
    ){
       Row(
           verticalAlignment = Alignment.CenterVertically,
           modifier = Modifier.fillMaxWidth()
       ){
           Icon(
               imageVector = Icons.Default.Search,
               contentDescription = "Search"
           )
           Spacer(modifier = Modifier.width(8.dp))
           Text(
               text = "Search any recipes",
               fontSize = 16.sp,
               color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f))
       }
    }
}

@Composable
private fun TopBar(

){
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ){
        Text(
            text = "Hi Mitchelle!",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )

        Text(
            text = "Got a tasty dish in Mind?",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(45.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = RoundedCornerShape(12.dp))
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f))
                .padding(horizontal = 16.dp)
        )
    }
}