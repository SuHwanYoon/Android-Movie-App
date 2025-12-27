package com.yoon.openmovie.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.movie_detail.domain.models.MovieDetail
import com.yoon.openmovie.ui.detail.components.DetailTopContent


@Composable
fun MovieDetailScreen(
    modifier: Modifier = Modifier,
    movieDetailViewModel: DetailViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit
) {
    val state by movieDetailViewModel.detailState.collectAsStateWithLifecycle()
    Box(modifier = modifier.fillMaxSize()) {
        // error 발생시 나올 Text
        AnimatedVisibility(
            visible = state.error != null,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Text(
                state.error ?: "unknown error",
                color = MaterialTheme.colorScheme.error,
                maxLines = 2
            )
        }
        // error없을시 나올 UI
        AnimatedVisibility(visible = !state.isLoading && state.error == null) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val boxHeight = maxHeight
                val topItemHeight = boxHeight * .4f
                val bodyItemHeight = boxHeight * .6f
                state.movieDetail?.let {movieDetail ->
                    DetailTopContent(
                        movieDetail = movieDetail,
                        modifier = Modifier.height(topItemHeight).align(Alignment.TopCenter)
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    val mockMovieDetail = MovieDetail(
        backdropPath = "/backdrop.jpg",
        genreIds = listOf("28", "12"),
        id = 1,
        originalLanguage = "en",
        originalTitle = "Sample Movie",
        overview = "Sample overview",
        popularity = 100.0,
        posterPath = "/w500/pB8BM7pdSp6B6Ih7QZ4DrQ3PmJK.jpg",
        releaseDate = "2023-01-01",
        title = "Sample Movie",
        voteAverage = 7.5,
        voteCount = 100,
        video = false,
        cast = emptyList(),
        language = listOf("en"),
        productionCountry = listOf("US"),
        reviews = emptyList(),
        runTime = "120 min"
    )

    val mockState = DetailState(
        movieDetail = mockMovieDetail,
        isLoading = false,
        error = null,
        movies = emptyList(),
        isMovieLoading = false
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // error 발생시 나올 Text
        AnimatedVisibility(
            visible = mockState.error != null,
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Text(
                mockState.error ?: "unknown error",
                color = MaterialTheme.colorScheme.error,
                maxLines = 2
            )
        }
        // error없을시 나올 UI
        AnimatedVisibility(visible = !mockState.isLoading && mockState.error == null) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val boxHeight = maxHeight
                val topItemHeight = boxHeight * .4f
                val bodyItemHeight = boxHeight * .6f
                mockState.movieDetail?.let { movieDetail ->
                    DetailTopContent(
                        movieDetail = movieDetail,
                        modifier = Modifier.height(topItemHeight).align(Alignment.TopCenter)
                    )
                }
            }
        }
    }
}