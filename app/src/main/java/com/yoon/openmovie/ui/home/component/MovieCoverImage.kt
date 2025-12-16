package com.yoon.openmovie.ui.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.ui.home.itemSpacing
import com.yoon.openmovie.utils.K

@Composable
fun MovieCoverImage(
    modifier: Modifier = Modifier,
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
    // size는 Box의 크기를 지정
    // padding(itemSpacing)은 Box 내부에 일정한 여백을 추가
    // clickable은 Box가 클릭 가능하도록 설정하고,
    // 클릭 시 onMovieClick 람다 함수를 호출하여 영화 ID를 전달
    Box(
        modifier = modifier
            .size(width = 150.dp, height = 250.dp)
            .padding(itemSpacing)
            .clickable { onMovieClick(movie.id) }
    ) {
        // AsyncImage 컴포저블을 사용하여 이미지 로드 및 표시
        // matchParentSize()는 이미지가 Box의 전체 크기를 차지하도록 설정
        // clip은 이미지의 모서리를 MaterialTheme.shapes.medium 모양으로 자름
        // shadow는 이미지에 그림자 효과를 추가
        // contentScale = ContentScale.Crop는 이미지가 Box에 맞게 잘림 없이 채워지도록 설정
        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            modifier = Modifier
                .matchParentSize()
                .clip(MaterialTheme.shapes.medium)
                .shadow(elevation = 4.dp),
            contentScale = ContentScale.Crop
        )
        // MovieCard 컴포저블을 사용하여 북마크 아이콘을 표시
        // align(Alignment.TopEnd)는 아이콘을 Box의 오른쪽 상단에 배치
        // padding(4.dp)은 아이콘 내부에 일정한 여백을 추가
        MovieCard(
            shapes = CircleShape,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.BookmarkBorder,
                contentDescription = "Bookmark",
                modifier = Modifier.padding(4.dp)
            )
        }
        // Surface 컴포저블을 사용하여 영화 제목을 표시하는 배경을 만듦
        // align(Alignment.BottomCenter)는 Surface를 Box의 하단 중앙에 배치
        // fillMaxWidth()는 Surface가 Box의 전체 너비를 차지하도록 설정
        // color는 Surface의 배경 색상을 검정색의 80% 불투명도로 설정
        // contentColor는 Surface 내부의 콘텐츠 색상을 흰색으로 설정
        // shape는 Surface의 모서리를 아래쪽 끝과 시작 부분에서 각각 30dp 둥글게 만듦
        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            color = Color.Black.copy(.8f),
            contentColor = Color.White,
            shape = RoundedCornerShape(bottomEnd = 30.dp, bottomStart = 30.dp)

        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = movie.title, maxLines = 1)
            }
        }
    }
}
