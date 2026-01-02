package com.yoon.openmovie.ui.detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.movie_detail.domain.models.MovieDetail
import com.yoon.openmovie.movie_detail.domain.models.Review
import com.yoon.openmovie.ui.home.component.MovieCard
import com.yoon.openmovie.ui.home.component.MovieCoverImage
import com.yoon.openmovie.ui.home.defaultPadding
import com.yoon.openmovie.ui.home.itemSpacing

/**
 * 영화 상세 화면의 주요 본문 콘텐츠를 렌더링하는 Composable 함수입니다.
 *
 * 이 컴포넌트는 [LazyColumn] 내에 구성되어 있으며, 영화의 상세 정보(장르, 상영 시간 등)를 보여주는
 * 카드형 UI를 포함하고 있습니다. 현재 구현에서는 상단 메타데이터 카드만 표시하지만,
 * 추후 관련 영화 목록이나 배우 정보 등을 스크롤 가능한 형태로 확장할 수 있는 구조입니다.
 *
 * @param modifier [LazyColumn]에 적용할 수정자(Modifier). 기본값은 [Modifier]입니다.
 * @param movieDetail 장르, 상영 시간 등 영화에 대한 구체적인 데이터를 담고 있는 객체.
 * @param movies 추천 영화 또는 관련 영화 목록 (현재 코드 스니펫에서는 직접 사용되지 않으나 확장성을 위해 존재).
 * @param isMovieLoading 관련 영화 목록을 불러오는 중인지 나타내는 로딩 상태 플래그.
 * @param fetchMovies 관련 영화 목록 로딩을 트리거하는 콜백 함수.
 * @param onMovieClick 관련 영화 항목을 클릭했을 때 호출되는 콜백 (영화 ID 전달).
 * @param onActorClick 배우 정보를 클릭했을 때 호출되는 콜백 (배우 ID 전달).
 */
@Composable
fun DetailBodyContent(
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail,
    movies: List<Movie>,
    isMovieLoading: Boolean,
    fetchMovies: () -> Unit,
    onMovieClick: (Int) -> Unit,
    onActorClick: (Int) -> Unit
) {
    LazyColumn(modifier) {
        item {
            Card(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(defaultPadding)
                ) {
                    // 장르와 런타임의 외부 Row
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // 장르를 표시하는 내부 Row
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 각 장르를 Text로 표시
                            // 장르 사이에 구분 기호(•) 추가
                            // genreIds 리스트를 빠르게 순회하며 인덱스와 값을 함께 사용
                            // fastForEachIndexed는 성능 최적화를 위해 사용
                            // 마지막 장르 뒤에는 구분 기호를 추가하지 않음
                            movieDetail.genreIds.fastForEachIndexed { index, genreText ->
                                Text(
                                    text = genreText,
                                    modifier = Modifier.padding(6.dp),
                                    maxLines = 1,
                                    style = MaterialTheme.typography.bodySmall
                                )
                                if (index != movieDetail.genreIds.lastIndex) {
                                    Text(
                                        text = " \u2022 ",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                        // 런타임을 표시하는 Text
                        Text(
                            text = movieDetail.runTime,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                    Spacer(modifier = Modifier.height(itemSpacing))
                    // 영화 제목과 개요 텍스트
                    Text(
                        text = movieDetail.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Text(
                        text = movieDetail.overview,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(itemSpacing))
                    // 액션 아이콘 버튼들을 표시하는 Row
                    Row(modifier = Modifier.fillMaxSize()) {
                        // ActionIcon 열거형의 각 항목에 대해 ActionIconBtn 컴포저블을 생성
                        // 모든 아이콘에 반투명 검은색 배경을 사용
                        ActionIcon.entries.forEachIndexed { index, actionIcon ->
                            ActionIconBtn(
                                icon = actionIcon.icon,
                                contentDescription = actionIcon.contentDescription,
                                bgColor = Color.Black.copy(.5f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(itemSpacing))
                    // Cast & Crew텍스트와 화살표 아이콘을 나타내는 Row 컨테이너
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = itemSpacing),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Cast & Crew",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(onClick = {/* ToDo */ }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                contentDescription = "Go to Cast and Crew Details"
                            )
                        }
                    }
                    // 배우 목록 컴포저블 호출
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(movieDetail.cast) {
                            // 각 배우 항목에 대해 ActorItem 컴포저블을 생성
                            // 클릭 시 onActorClick 콜백 호출
                            ActorItem(
                                cast = it,
                                modifier = Modifier.clickable { onActorClick(it.id) }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(itemSpacing))
                    // 영화 언어, 제작 국가 등의 추가 정보 표시
                    MovieInfoItem(
                        infoItem = movieDetail.language,
                        title = "Spoken Language: "
                    )

                    Spacer(modifier = Modifier.height(itemSpacing))
                    MovieInfoItem(
                        infoItem = movieDetail.productionCountry,
                        title = "Production countries: "
                    )

                    Spacer(modifier = Modifier.height(itemSpacing))
                    // 리뷰 섹션 제목
                    Text(
                        text = "Reviews",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    // 리뷰 컴포저블 호출
                    Spacer(modifier = Modifier.height(itemSpacing))
                    Review(reviews = movieDetail.reviews)
                    // 하단 연관영화 표시
                    Spacer(modifier = Modifier.height(itemSpacing))
                    MoreLikeThis(
                        fetchMovies = fetchMovies,
                        isMovieLoading = isMovieLoading,
                        movies = movies,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }

}

/**
 * 상세 화면에서 사용할 수 있는 액션 버튼 아이콘들을 정의한 열거형 클래스입니다.
 *
 * 이 클래스는 각 액션(북마크, 공유, 다운로드)에 해당하는 [ImageVector] 아이콘 리소스와
 * 접근성을 위한 [contentDescription] 문자열을 캡슐화합니다.
 *
 * @property icon UI에 표시될 벡터 이미지 아이콘.
 * @property contentDescription 스크린 리더 등을 위한 아이콘 설명.
 */
private enum class ActionIcon(val icon: ImageVector, val contentDescription: String) {
    BookMark(Icons.Default.BookmarkBorder, "Bookmark Icon"),
    Share(Icons.Default.Share, "Share Icon"),
    Download(Icons.Default.Download, "Download Icon")
}

/**
 * 영화 상세 화면에서 사용되는 액션 아이콘 버튼(북마크, 공유, 다운로드 등)을 표시하는 Composable 함수입니다.
 *
 * 원형 모양의 배경을 가진 카드 형태의 버튼을 생성하며, 내부에는 전달받은 아이콘 벡터를 렌더링합니다.
 * 주로 사용자 인터랙션을 위한 작은 원형 버튼을 만들 때 사용됩니다.
 *
 * @param modifier 버튼의 레이아웃을 수정하는 [Modifier]. 기본값은 [Modifier]입니다.
 * @param icon 버튼 내부에 표시할 벡터 이미지([ImageVector]).
 * @param contentDescription 접근성을 위한 아이콘 설명 텍스트. 기본값은 null입니다.
 * @param bgColor 버튼의 배경 색상. 기본값은 투명도가 적용된 검은색(Color.Black.copy(.8f))입니다.
 */
@Composable
private fun ActionIconBtn(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    bgColor: Color = Color.Black.copy(.8f)
) {
    MovieCard(
        shapes = CircleShape,
        modifier = modifier.padding(4.dp),
        bgColor = bgColor
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.padding(4.dp)
        )
    }
}

@Composable
private fun MovieInfoItem(infoItem: List<String>, title: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        infoItem.forEach {
            Text(
                text = it,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun Review(
    modifier: Modifier = Modifier,
    reviews: List<Review>
){
    // viewMore 상태를 기억하여 리뷰 전체 보기/접기 기능 구현
    val (viewMore, setViewMore) = remember {
        mutableStateOf(false)
    }
    // 리뷰가 2개 초과이고, viewMore가 false일 때는 처음 2개의 리뷰만 표시
    val defaultReview = if (reviews.size > 2 ){
        reviews.take(2)
    }else {
        reviews
    }
    // viewMore 상태에 따라 전체 리뷰 또는 기본 리뷰 목록을 선택
    val movieReviews = if (viewMore) reviews else defaultReview
    // btnText 상태에 따라 버튼 텍스트 결정
    val btnText = if (viewMore) "View Less" else "View More"
    Column(modifier) {
        // 각 리뷰 항목을 ReviewItem 컴포저블로 렌더링
        movieReviews.forEach {review ->
            ReviewItem(review = review)
            Spacer(modifier = Modifier.height(itemSpacing))
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(itemSpacing))
        }
        TextButton(onClick = { setViewMore(!viewMore) }) {
            Text(text = btnText)
        }
    }

}

@Composable
fun MoreLikeThis(
    modifier: Modifier = Modifier,
    fetchMovies: () -> Unit,
    isMovieLoading: Boolean,
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
){
    // LaunchedEffect를 사용하여 컴포저블이 처음 구성될 때 영화 데이터를 가져오는 작업 수행
    LaunchedEffect(key1 = true) {
        fetchMovies()
    }
    Column(modifier) {
        Text(
            text = "More Like This",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        // 수평으로 스크롤 가능한 영화 목록을 표시하는 LazyRow
        LazyRow{
            // 로딩 중일 때 원형 진행 표시기를 표시
            item {
                AnimatedVisibility(visible = isMovieLoading) {
                    CircularProgressIndicator()
                }
            }
            // 영화 목록을 반복하여 각 영화를 MovieCoverImage 컴포저블로 렌더링
            items(movies){
                MovieCoverImage(movie = it, onMovieClick = onMovieClick)
            }
        }
    }
}