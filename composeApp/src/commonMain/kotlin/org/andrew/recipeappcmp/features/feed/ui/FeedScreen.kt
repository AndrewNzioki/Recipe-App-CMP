package org.andrew.recipeappcmp.features.feed.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import org.andrew.recipeappcmp.features.common.domain.entities.RecipeItem
import org.andrew.recipeappcmp.features.common.ui.components.ErrorContent
import org.andrew.recipeappcmp.features.common.ui.components.Loader
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FeedRoute(
    navigateToDetail: (Long) -> Unit,
    navigateToSearch: () -> Unit,
    feedViewModel: FeedViewModel = koinViewModel()
){
    val feedUiState = feedViewModel.feedUiState.collectAsStateWithLifecycle()

    FeedScreen(
        navigateToDetail = navigateToDetail,
        feedUiState = feedUiState.value,
        navigateToSearch = navigateToSearch
    )
}


@Composable
fun FeedScreen(
    navigateToDetail: (Long) -> Unit,
    feedUiState: FeedUiState,
    navigateToSearch: () -> Unit
){
    val recipes = feedUiState.recipesList
    Scaffold(
        topBar = {TopBar(navigateToSearch)}
    ) { innerPadding ->

        when{
            feedUiState.recipesListIsLoading -> {
                Loader()
            }

            feedUiState.recipesListError != null -> {
                ErrorContent(feedUiState.recipesListError)
            }

            recipes != null -> {
                FeedContent(
                    innerPadding = innerPadding,
                    recipes = recipes,
                    navigateToDetail = navigateToDetail)
            }
        }

    }
}



@Composable
private fun FeedContent(
    navigateToDetail: (Long) -> Unit,
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
                 recipes = recipes.reversed(),
                 navigateToDetail = navigateToDetail
             )
          }

            recipesOfTheWeek(
                title = "Recipes Of the Week",
                recipes = recipes,
                navigateToDetail = navigateToDetail
            )
        }


    }
}

@Composable
private fun TopRecipesList(
    navigateToDetail: (Long) -> Unit,
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
                    imageModifier = imageModifier.clickable{
                        navigateToDetail(recipe.id)
                    }
                )
            }
        }
    }
}


private fun LazyGridScope.recipesOfTheWeek(
    navigateToDetail: (Long) -> Unit,
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
            imageModifier = imageModifier.clickable{
                navigateToDetail(recipe.id)
            }
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
    navigateToSearch: () -> Unit,
){
    Column(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ){
        Text(
            text = "Hi Allan!",
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
                .clickable{
                    navigateToSearch()
                }
        )
    }
}