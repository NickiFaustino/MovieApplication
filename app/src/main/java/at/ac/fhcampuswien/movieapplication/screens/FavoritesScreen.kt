package at.ac.fhcampuswien.movieapplication.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.movieapplication.models.Movie
import at.ac.fhcampuswien.movieapplication.navigation.MovieScreens
import at.ac.fhcampuswien.movieapplication.viewmodels.FavoritesViewModel
import at.ac.fhcampuswien.movieapplication.widgets.MovieRow
import at.ac.fhcampuswien.movieapplication.widgets.SimpleTopAppBar

@Composable
fun FavoritesScreen(navController: NavController, viewModel: FavoritesViewModel){
    Scaffold(topBar = {
        SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) { //bekommt die kleinere TopAppBar mit backarrow
            Text(text = "My Favorite Movies")
        }
    }){
        MainContent(movieList = viewModel.favoriteMovies, navController = navController) //die movielist die unten verwendet wird bekommt nur eine Liste von FavoriteMovies nicht mehr die ganze Liste
    }
}

@Composable
fun MainContent(movieList: List<Movie>, navController: NavController){
    Column(modifier = Modifier.padding(12.dp)) {
        LazyColumn {
            items(items = movieList){ movie -> //Liste übergeben und wird dann unten in MovieRows angezeigt
                MovieRow(
                    movie = movie,
                    onItemClick = {
                            item ->
                        navController.navigate(route = MovieScreens.DetailScreen.name+"/$item") //on itemclick wird dann zum DetailScreen navigiert
                    }
                )
            }
        }
    }
}