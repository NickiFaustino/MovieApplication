package at.ac.fhcampuswien.movieapplication.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import at.ac.fhcampuswien.movieapplication.models.Movie

    class FavoritesViewModel : ViewModel() {

        private val _favoriteMovies = mutableStateListOf<Movie>() // mutableListOf<Movie>() //assigned der Variable _favoriteMovies eine mutable liste
        val favoriteMovies: List<Movie> //diese Variable wird in FavoriteScreen.kt verwendet
            get() = _favoriteMovies //getter um der Variable favoriteMovies eine nicht mutable liste von _favoriteMovies zu geben
        //Diese ist nicht mutable damit der state bei einer Änderung von dieser Liste nichts gemerkt wird
        //Es wurde aus dem Grund gemacht weil _favoriteMovies private ist, damit es nicht von anderen Klassen geändert werden kann, nur durch das viewmodel


        fun addToFavorites(movie: Movie) { //mit dieser Funktion werden movies geaddet vorausgesetzt sie sind noch nicht drinnen
            if(!exists(movie = movie)){
                _favoriteMovies.add(movie)
            }
        }

        fun removeFromFavorites(movie: Movie){ //mit dieser Funktion können bestimmte movies removed werden
            _favoriteMovies.remove(movie)
        }

        private fun exists(movie: Movie) : Boolean { //hier wird er Methode ein Movie übergeben und ein Boolean returned, Hier schaut man ob irgendeines der Movies die sich in der Liste ganz oben befinden schon in favorites drinnen ist
            return _favoriteMovies.any {m -> m.id == movie.id }
        }

        fun isFavorite(movie: Movie) : Boolean { //hier wird exists oben als return wert genommen
            return exists(movie)
        }
    }



