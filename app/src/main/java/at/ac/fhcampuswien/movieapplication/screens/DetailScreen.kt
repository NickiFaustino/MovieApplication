package at.ac.fhcampuswien.movieapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.movieapplication.models.Movie
import at.ac.fhcampuswien.movieapplication.models.getMovies
import at.ac.fhcampuswien.movieapplication.viewmodels.FavoritesViewModel
import at.ac.fhcampuswien.movieapplication.widgets.FavoriteIcon
import at.ac.fhcampuswien.movieapplication.widgets.HorizontalScrollableImageView
import at.ac.fhcampuswien.movieapplication.widgets.MovieRow
import at.ac.fhcampuswien.movieapplication.widgets.SimpleTopAppBar

fun filterMovie(movieId: String?): Movie {
    return getMovies().filter { it.id == movieId}[0]
}

@Composable
fun DetailScreen(navController: NavController, favoritesViewModel: FavoritesViewModel, movieId: String?){
    val movie = filterMovie(movieId = movieId) //variable movie wird ein Movie assigned was die gleiche ID hat wie die die gepassed wurde

    Scaffold(
        topBar = {
            SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) { //popbackstack um zum vorherigen Screen zu kommen
                Text(text = movie.title)
            }
        }
    ) {
        MainContent(movie = movie, favoritesViewModel)
    }
}

@Composable
fun MainContent(movie: Movie, favoritesViewModel: FavoritesViewModel){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            MovieRow(movie = movie) {
                FavoriteIcon( //gleich wie in HomeScreen
                    movie = movie,
                    isFav = favoritesViewModel.isFavorite(movie)
                ){ m ->
                    if(favoritesViewModel.isFavorite(m)){
                        favoritesViewModel.removeFromFavorites(m)
                    } else {
                        favoritesViewModel.addToFavorites(m)
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Divider()

            Text(text = "Movie Images", style = MaterialTheme.typography.h5)

            HorizontalScrollableImageView(movie = movie) //Scrollable von MovieWidgets.kt
        }
    }
}