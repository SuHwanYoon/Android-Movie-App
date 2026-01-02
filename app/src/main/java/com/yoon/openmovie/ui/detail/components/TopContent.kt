package com.yoon.openmovie.ui.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yoon.openmovie.R
import com.yoon.openmovie.movie_detail.domain.models.MovieDetail
import com.yoon.openmovie.ui.home.component.MovieCard
import com.yoon.openmovie.ui.home.defaultPadding
import com.yoon.openmovie.ui.home.itemSpacing
import com.yoon.openmovie.ui.theme.primaryLightHighContrast
import com.yoon.openmovie.utils.K
import java.util.Locale


/**
 * 영화 상세 화면의 상단 콘텐츠 섹션을 표시하는 컴포저블입니다.
 * 주로 영화의 포스터 이미지를 전체 화면 너비로 보여주는 역할을 합니다.
 *
 * 이 컴포저블은 Coil 라이브러리의 [AsyncImage]를 사용하여 [MovieDetail] 객체에 포함된
 * `posterPath`를 기반으로 포스터 이미지를 비동기적으로 로드합니다.
 *
 * 주요 기능:
 * - 기본 이미지 URL과 포스터 경로를 조합하여 이미지 요청 생성.
 * - 로딩 중 [R.drawable.bg_image_movie]를 플레이스홀더로 표시.
 * - 이미지를 잘라내어(Crop) 꽉 찬 화면으로 표시.
 * - 로드 실패 시 스택 트레이스를 출력.
 *
 * @param modifier 루트 Box 레이아웃에 적용할 [Modifier]. 기본값은 [Modifier]입니다.
 * @param movieDetail 영화의 세부 정보를 담고 있는 데이터 객체. 포스터 경로(`posterPath`)를 참조합니다.
 */
@Composable
fun DetailTopContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail
) {
    // Coil을 사용하여 상세 페이지 에서 영화 포스터 이미지를 가져오는 빌더 생성
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data("${K.BASE_IMAGE_URL}${movieDetail.posterPath}")
        .crossfade(true)
        .build()

    // 상단 영화포스터 가져오는 빌더를 넣을 Box 컴포저블 생성
    Box(modifier = modifier.fillMaxSize()) {
        // 포스터 이미지를 비동기적으로 로드하고 표시
        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            onError = {
                it.result.throwable.printStackTrace()
            },
            // 이미지 로딩 중 표시할 플레이스홀더  이미지 설정
            placeholder = painterResource(id = R.drawable.bg_image_movie)
        )
        // 영화 상세 정보 컴포저블 호출
        MovieDetailComponent(
            rating = movieDetail.voteAverage,
            releaseDate = movieDetail.releaseDate,
            modifier = Modifier.align(Alignment.BottomStart)
        )
    }
}

//내부에서만 사용할 컴포저블함수
@Composable
private fun MovieDetailComponent(
    modifier: Modifier = Modifier,
    rating: Double,
    releaseDate: String
) {
    // 수직으로 UI요소를 정렬할 목적의 Column 컴포저블
    Column(modifier) {
        MovieCard(modifier = Modifier.padding(horizontal = defaultPadding)) {
            // 외부 Row 컴포저블(Main Row) : 별점 영역과 출시 날짜 영역 전체를 묶음
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                // 별점과 출시날짜를 나타내며 가운데 분리선이 있는 내부 Row 컴포저블
                Row(
                    modifier = Modifier.padding(4.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Rating Star Icon",
                        tint = Color.Yellow
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = String.format(Locale.US, "%.1f", rating))
                }
                Spacer(modifier = Modifier.width(itemSpacing))
                VerticalDivider(modifier = Modifier.height(16.dp))
                Text(
                    text = releaseDate,
                    modifier = Modifier.padding(6.dp),
                    maxLines = 1
                )
            }
            // Watch Now 버튼 + Watch Trailer 버튼을 나타낼 Row 컴포저블
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = defaultPadding)
        ) {
            // watch now 버튼
            Card(
                onClick = { /* TODO: Implement watch now functionality */ },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(topStart = 30.dp, bottomStart = 30.dp)
            ) {
                Row(modifier = Modifier.padding(4.dp)) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play Icon")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Watch Now")
                }
            }
            // watch trailer 버튼
            Card(
                onClick = {/* TODO */ },
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                    contentColor = primaryLightHighContrast
                ),
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp)
            ) {
                Row(modifier = Modifier.padding(4.dp)) {
                    Icon(imageVector = Icons.Filled.Movie, contentDescription = "Movie Icon")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Watch Trailer")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailTopContentPreview() {
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
    DetailTopContent(movieDetail = mockMovieDetail)
}