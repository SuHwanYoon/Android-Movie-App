package com.yoon.openmovie.ui.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yoon.openmovie.R
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.utils.K

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
            .fillMaxSize()
            .clickable { onMovieClick(movie.id) }
    ){
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
            onError = {it.result.throwable.printStackTrace()},
            placeholder = painterResource(id = R.drawable.bg_image_movie)
        )
    }
}

@Composable
fun MovieDetail (
    modifier: Modifier = Modifier,

){

}