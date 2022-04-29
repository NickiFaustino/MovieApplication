package at.ac.fhcampuswien.movieapplication.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import at.ac.fhcampuswien.movieapplication.screens.DetailScreen
import at.ac.fhcampuswien.movieapplication.screens.FavoritesScreen
import at.ac.fhcampuswien.movieapplication.screens.HomeScreen
import at.ac.fhcampuswien.movieapplication.viewmodels.FavoritesViewModel

@Composable
fun MovieNavigation(){
    val navController = rememberNavController() // create NavController instance

    val favoritesViewModel: FavoritesViewModel = viewModel() //weil alle Screens auf Navigation zugriff haben wird hier viemodel definiert
    //hier wird eine favoritesViewModel definiert die dann bei allen eingefügt wird

    NavHost(navController = navController, startDestination = MovieScreens.HomeScreen.name){ //Container von Screens
        composable(route = MovieScreens.HomeScreen.name) { //Name (steht in der enum Class) zu Composable assignen
            HomeScreen(navController, favoritesViewModel)
        }

        composable(route = MovieScreens.FavoritesScreen.name) {
            FavoritesScreen(navController, favoritesViewModel)
        }

        composable(
            route = MovieScreens.DetailScreen.name+"/{movieId}",// placeholder for arguments
            arguments = listOf(navArgument(name = "movieId"){   // define arguments that can be passed
                type = NavType.StringType //definiere Typ vom argument
            })) { navBackStackEntry ->

            DetailScreen(
                navController = navController,
                favoritesViewModel,
                movieId = navBackStackEntry.arguments?.getString("movieId") // pass the value of movieId argument to the DetailScreen composable
            )
        }
    }
}