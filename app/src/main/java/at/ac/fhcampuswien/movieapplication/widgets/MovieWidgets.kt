package at.ac.fhcampuswien.movieapplication.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import at.ac.fhcampuswien.movieapplication.models.Movie
import at.ac.fhcampuswien.movieapplication.models.getMovies
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Preview(showBackground = true)
@Composable
fun FavoriteIcon( //wird ein movie übergeben, ein boolean isFav und eine Funktion onFavCklicked die das gecklickte movie speichert
    movie: Movie = getMovies()[0],
    isFav: Boolean = false,
    onFavClicked: (Movie) -> Unit = {},
){
    IconButton(
        modifier = Modifier.width(80.dp),
        onClick = { onFavClicked(movie) } //hier wird der onFavClicked Funktion die movie Variable von oben übergeben
    ) {
        Icon(
            tint = MaterialTheme.colors.secondary,
            imageVector =
            if (isFav) Icons.Default.Favorite //if isFav is true also wenn es geclicked wird --> mache volles Herz
            else Icons.Default.FavoriteBorder, //wenn false dann mach border style
            contentDescription = "add to favorites")
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun MovieRow(
    movie: Movie = getMovies()[0],
    onItemClick: (String) -> Unit = {},
    content: @Composable () -> Unit = {}
    ) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Card(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        .clickable {
            onItemClick(movie.id)
        },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = 6.dp) {

        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){

                Surface(modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp)) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(movie.images[0])
                            .crossfade(true)
                            .build(),
                        contentDescription = "Movie poster",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape)
                    )
                }

                Column(modifier = Modifier
                    .padding(4.dp)
                    .widthIn(200.dp, 200.dp)) {
                    Text(text = movie.title,
                        style = MaterialTheme.typography.h6)

                    Text(text = "Director ${movie.director}",
                        style = MaterialTheme.typography.caption)

                    Text(text = "Released: ${movie.year}",
                        style = MaterialTheme.typography.caption)

                    AnimatedVisibility(visible = expanded) {
                        Column {
                            Text(text = "Plot: ${movie.plot}", style = MaterialTheme.typography.caption)

                            Divider(modifier = Modifier.padding(3.dp))

                            Text(text = "Genre: ${movie.genre}", style = MaterialTheme.typography.caption)
                            Text(text = "Actors: ${movie.actors}", style = MaterialTheme.typography.caption)
                            Text(text = "Rating: ${movie.rating}", style = MaterialTheme.typography.caption)
                        }
                    }


                    Icon(imageVector =
                    if (expanded) Icons.Filled.KeyboardArrowDown
                    else Icons.Filled.KeyboardArrowUp,
                        contentDescription = "expand",
                        modifier = Modifier
                            .size(25.dp)
                            .clickable {
                                expanded = !expanded
                            },
                        tint = Color.DarkGray)
                }

                content()
                /*
                IconButton(
                    modifier = Modifier.width(80.dp),
                    onClick = { onFavClicked(movie) }
                ) {
                    Icon(
                        tint = MaterialTheme.colors.secondary,
                        imageVector =
                        if (isFav) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = "add to favorites")
                }

                 */
            }
        }
    }
}

@Composable
fun HorizontalScrollableImageView(movie: Movie) {
    LazyRow {
        items(movie.images) { image ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(240.dp),
                elevation = 4.dp
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Movie poster",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}