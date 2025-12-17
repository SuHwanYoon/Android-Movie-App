package com.yoon.openmovie.ui.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yoon.openmovie.R
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.ui.home.defaultPadding
import com.yoon.openmovie.ui.home.itemSpacing
import com.yoon.openmovie.utils.K
import java.util.Locale

// TopContent 컴포저블 함수는 상단 콘텐츠를 표시하는 UI 구성 요소입니다.
// modifier 매개변수는 UI 요소의 레이아웃과 스타일을 조정하는 데 사용됩니다.
// movie 매개변수는 표시할 영화 데이터를 나타냅니다.
// onMovieClick 매개변수는 영화 항목이 클릭될 때 호출되는 람다 함수입니다.
@Composable
fun TopContent(
    modifier: Modifier,
    movie: Movie,
    onMovieClick: (id: Int) -> Unit,
) {
    // 이미지 요청을 생성하여 영화 포스터 이미지를 로드
    // coil 을 사용해서 이미지를 불러오기
    // LocalContext.current는 현재 컴포저블 함수의 컨텍스트를 가져오는 역할
    // K.BASE_IMAGE_URL은 이미지의 기본 URL을 나타내며,
    // movie.posterPath는 특정 영화의 포스터 이미지 경로를 나타냅니다.
    // crossfade(true)는 이미지 로딩 시 흐린 이미지에서 점점 선명한 이미지로 전환되는 효과를 활성화
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data("${K.BASE_IMAGE_URL}${movie.posterPath}")
        .crossfade(true)
        .build()
    // Box 레이아웃을 사용하여 영화 포스터 이미지를 표시
    // fillMaxSize()는 Box가 가능한 최대 크기를 차지하도록 설정
    // clickable은 Box가 클릭 가능하도록 설정하고,
    // 클릭 시 onMovieClick 람다 함수를 호출하여 영화 ID를 전달
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onMovieClick(movie.id) }
    ) {
        // AsyncImage 컴포저블을 사용하여 이미지 로드 및 표시
        // model 매개변수에는 앞서 생성한 이미지 요청을 전달
        // contentDescription은 이미지에 대한 설명을 제공하지만, null로 설정하여 생략
        // matchParentSize()는 이미지가 부모 Box의 크기에 맞게 조정되도록 설정
        // ContentScale.Crop은 이미지가 Box를 완전히 채우도록 크롭되도록 설정
        // onError는 이미지 로딩 중 오류가 발생했을 때 호출되는 람다 함수로,
        // 여기서는 오류의 스택 트레이스를 출력
        // placeholder는 이미지가 로드되는 동안 표시할 대체 이미지를 지정
        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            onError = { it.result.throwable.printStackTrace() },
            placeholder = painterResource(id = R.drawable.bg_image_movie)
        )
        // MovieDetail 컴포저블을 호출하여 영화 세부 정보를 표시
        // rating, title, genre 매개변수에는 각각 영화의 평점, 제목, 장르 목록을 전달
        // modifier 매개변수에는 영화 세부 정보가 Box의 하단 시작 부분에 정렬되고,
        // 아래쪽에 20dp의 패딩이 적용되도록 설정
        MovieDetail(
            rating = movie.voteAverage,
            title = movie.title,
            genre = movie.genreIds,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 20.dp)
        )
    }
}

// MovieDetail 컴포저블 함수는 영화의 세부 정보를 표시하는 UI 구성 요소입니다.
// modifier 매개변수는 UI 요소의 레이아웃과 스타일을 조정하는 데 사용됩니다.
// rating 매개변수는 영화의 평점을 나타냅니다.
// title 매개변수는 영화의 제목을 나타냅니다.
// genre 매개변수는 영화의 장르 목록을 나타냅니다.
@Composable
fun MovieDetail(
    modifier: Modifier = Modifier,
    rating: Double,
    title: String,
    genre: List<String>,
) {
    // Column 레이아웃을 사용하여 영화 세부 정보를 수직으로 정렬
    // modifier.padding(defaultPadding)은 Column에 기본 패딩을 적용
    Column(modifier = modifier.padding(defaultPadding)) {
        // 별 아이콘과 평점을 표시하는 MovieCard 컴포저블 호출
        MovieCard {
            // Row 레이아웃을 사용하여 영화 제목과 평점을 가로로 정렬
            // padding(horizontal = 12.dp, vertical = 4.dp)로 좌우 여백을 넓게 설정하여
            // 평점 숫자가 여유롭게 표시되도록 함
            // horizontalArrangement는 Row 내의 항목들을 수평으로 중앙에 정렬
            // verticalAlignment는 Row 내의 항목들을 수직으로 중앙에 정렬
            Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon 컴포저블을 사용하여 별 아이콘을 표시
                // Icons.Filled.Star는 채워진 별 아이콘을 나타냄
                // contentDescription은 아이콘에 대한 설명을 제공
                // tint는 아이콘의 색상을 노란색으로 설정
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Rating",
                    tint = Color.Yellow
                )
                Spacer(modifier = Modifier.width(4.dp))
                // 평점을 소수점 한 자리까지만 표시 (예: 8.5, 6.3)
                // Locale.US를 사용하여 모든 지역에서 일관되게 "." (마침표)를 소수점으로 사용
                Text(text = String.format(Locale.US, "%.1f", rating))
            }
        }

        Spacer(modifier = Modifier.height(itemSpacing))
        // 영화제목을 표시하는 Text 컴포저블
        //  style는 제목의 텍스트 스타일을 MaterialTheme의 titleLarge로 설정
        // fontWeight는 텍스트를 굵게 표시하도록 설정
        // maxLines는 제목이 한 줄로 제한되도록 설정
        // color는 텍스트 색상을 흰색으로 설정
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(itemSpacing))

        // 영화 장르을 표시하는 MovieCard 컴포저블 호출
        MovieCard {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // genre 목록을 순회하며 각 장르를 Text 컴포저블로 표시
                // forEachIndexed는 인덱스와 함께 각 항목에 접근할 수 있도록 함
                // index가 0이 아닌 경우 즉 첫 번째 장르가 아닌 경우에만
                // VerticalDivider를 추가하여 장르들 사이에 구분선을 표시
                genre.forEachIndexed { index, genreText ->
                    if (index != 0) {
                        VerticalDivider(modifier = Modifier.height(16.dp))
                    }
                    // Text 컴포저블은 장르 텍스트를 표시하며,
                    // modifier.padding(6.dp).weight(1f)는 각 장르 텍스트에 6dp의 패딩을 적용하고,
                    // 가용 공간을 균등하게 분배하도록 설정
                    Text(
                        text = genreText,
                        modifier = Modifier
                            .padding(6.dp)
                            .weight(1f),
                        maxLines = 1
                    )
                    // 장르 목록의 마지막 항목이 아닌 경우에만
                    // VerticalDivider를 추가하여 여러 장르들 사이에 구분선을 표시
                    if (index != genre.lastIndex) {
                        VerticalDivider(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}


// PreviewMovieDetail 컴포저블 함수는 MovieDetail 컴포저블의 미리보기를 제공합니다.
@Preview(showBackground = true)
@Composable
private fun PreviewMovieDetail() {
    MovieDetail(
        rating = 8.5,
        title = "Sample Movie",
        genre = listOf("Action", "Adventure", "Comedy")
    )
}