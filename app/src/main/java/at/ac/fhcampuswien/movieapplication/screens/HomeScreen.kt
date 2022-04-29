package at.ac.fhcampuswien.movieapplication.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import at.ac.fhcampuswien.movieapplication.models.Movie
import at.ac.fhcampuswien.movieapplication.models.getMovies
import at.ac.fhcampuswien.movieapplication.navigation.MovieScreens
import at.ac.fhcampuswien.movieapplication.viewmodels.FavoritesViewModel
import at.ac.fhcampuswien.movieapplication.widgets.FavoriteIcon
import at.ac.fhcampuswien.movieapplication.widgets.MovieRow

@Composable
fun HomeScreen(navController: NavController, viewModel: FavoritesViewModel){
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Movies") },
            actions = {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More")
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(onClick = { navController.navigate(route = MovieScreens.FavoritesScreen.name) }) { //navigation zum FavoriteScreen
                        Row(modifier = Modifier.clickable { navController.navigate(MovieScreens.FavoritesScreen.name) }) {
                            Icon(imageVector = Icons.Default.Favorite, contentDescription = "Favorites", modifier = Modifier.padding(4.dp))
                            Text(text = "Favorites", modifier = Modifier
                                .width(100.dp)
                                .padding(4.dp))
                        }
                    }
                }
            }
        )
    }) {
        MainContent(navController = navController, favoritesViewModel = viewModel) //hier bekommt MainContent seinen NavController für die navigation zum DetailScreen
    } //und sein viewModel er bekommt alles vom HomeScreen
}

@Composable
fun MainContent(navController: NavController, favoritesViewModel: FavoritesViewModel, movies: List<Movie> = getMovies()){
    LazyColumn {
        items(movies){ movie ->
            MovieRow(
                movie = movie,
                onItemClick = { movieId -> navController.navigate(MovieScreens.DetailScreen.name+"/$movieId")}
            ) {
                FavoriteIcon( //hier wird dann favoriteIcon aufgerufen welches sich im MovieWidgets befindet
                    movie = movie, //movie von oben wird assigned
                    isFav = favoritesViewModel.isFavorite(movie) //hier wird jetzt eine Funktion vom FavoritesViewModel verwendet die isFavorite Methode *im FavoriteViewModel schauen
                ){ m -> //hier wird dem FavoriteIcon eine Funktion hinzugefügt
                    if(favoritesViewModel.isFavorite(m)){ //wenn es schon ein favorite ist dann remove es
                        favoritesViewModel.removeFromFavorites(m)
                    } else { //wenn es noch kein favorite ist dann add es
                        favoritesViewModel.addToFavorites(m)
                    }
                }
            }
        }
    }
}
