package com.yoon.openmovie.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.yoon.openmovie.ui.components.LoadingView
import com.yoon.openmovie.ui.detail.components.DetailBodyContent
import com.yoon.openmovie.ui.detail.components.DetailTopContent


/**
 * 특정 영화의 상세 정보를 표시하는 컴포저블 화면입니다.
 *
 * 이 화면은 다음과 같은 책임을 가집니다:
 * - [DetailViewModel]의 상태를 관찰하여 로딩, 에러, 성공 상태에 따른 UI를 관리합니다.
 * - 에러 발생 시 상단에 애니메이션과 함께 에러 메시지를 표시합니다.
 * - 데이터 로딩 중에는 [LoadingView]를 통해 로딩 인디케이터를 보여줍니다.
 * - [BoxWithConstraints]를 사용하여 화면을 크게 두 영역으로 나누어 렌더링합니다:
 *     - [DetailTopContent]: 화면 상단 40%를 차지하며, 영화의 포스터나 백드롭 이미지를 표시합니다.
 *     - [DetailBodyContent]: 화면 하단 60%를 차지하며, 출연진, 리뷰, 관련 영화 등 상세 정보를 표시합니다.
 * - 내비게이션 처리를 위한 콜백을 제공합니다:
 *     - 뒤로 가기 ([onNavigateUp]).
 *     - 관련 영화 클릭 ([onMovieClick]).
 *     - 출연 배우 클릭 ([onActorClick]).
 *
 * @param modifier 루트 레이아웃에 적용할 수정자(Modifier).
 * @param movieDetailViewModel 영화 상세 정보의 비즈니스 로직과 UI 상태를 보유한 ViewModel. 기본값은 [hiltViewModel]입니다.
 * @param onNavigateUp 사용자가 뒤로 가기 버튼을 눌렀을 때 호출되는 콜백.
 * @param onMovieClick 관련 영화 목록에서 항목을 클릭했을 때 호출되는 콜백 (영화 ID 전달).
 * @param onActorClick 출연진 목록에서 배우를 클릭했을 때 호출되는 콜백 (배우 ID 전달).
 */
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
                // 상단 포스터 이미지
                state.movieDetail?.let {movieDetail ->
                    DetailTopContent(
                        movieDetail = movieDetail,
                        modifier = Modifier.height(topItemHeight).align(Alignment.TopCenter)
                    )
                    // 하단 상세 정보
                    DetailBodyContent(
                       movieDetail = movieDetail,
                        movies = state.movies,
                        isMovieLoading = state.isMovieLoading,
                        fetchMovies = movieDetailViewModel::fetchMovie,
                        onMovieClick = onMovieClick,
                        onActorClick = onActorClick,
                        modifier = Modifier.align(Alignment.BottomCenter).height(bodyItemHeight)
                    )
                }
            }
            // 뒤로가기 버튼
            IconButton(onClick = onNavigateUp, modifier = Modifier.align(Alignment.TopStart)) {
                Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
            }
        }
    }
    // 로딩중 UI
    LoadingView(isLoading = state.isLoading)
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