package com.yoon.openmovie.ui.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.ui.home.itemSpacing

@Composable
fun BodyContent(
    modifier: Modifier = Modifier,
    discoverMovies: List<Movie>,
    trendingMovies: List<Movie>,
    onMovieClick: (id: Int) -> Unit,
) {
    // LazyColumn을 활용하여 스크롤 가능한 콘텐츠 표시
    // item은 item 블록 내에 각 항목의 UI 구성 요소를 정의
    // 중요: modifier 파라미터를 반드시 사용해야 함
    // 이전 문제: LazyColumn(modifier = Modifier)로 빈 Modifier를 사용하여
    // HomeScreen에서 전달한 .align(Alignment.BottomCenter)와 .heightIn(max = bodyItemHeight)가
    // 무시되었고, 이로 인해 BodyContent가 화면 하단에 제대로 배치되지 않았음
    // 해결 방법: modifier = modifier로 변경하여 부모에서 전달한 레이아웃 제약을 적용
    LazyColumn(modifier = modifier) {
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                // Discover Movies 섹션 헤더
                // Row 레이아웃을 사용하여 제목과 아이콘 버튼을 가로로 배치
                // fillMaxSize()는 Row가 가능한 최대 크기를 차지하도록 설정
                // padding(itemSpacing)은 Row 내부에 일정한 여백을 추가
                // horizontalArrangement = Arrangement.SpaceBetween는
                // Row 내의 항목들을 좌우 끝에 배치
                // verticalAlignment = Alignment.CenterVertically는
                // Row 내의 항목들을 수직으로 중앙에 정렬
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(itemSpacing),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 제목 텍스트
                    Text(
                        text = "Discover Movies",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    // 아이콘 버튼
                    IconButton(onClick = { /* TODO: Implement click action */ }) {
                        // 화살표 아이콘
                        // Icons.AutoMirrored.Filled.ArrowForwardIos는
                        // 자동으로 좌우 반전되는 화살표 아이콘을 나타냄
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = "Discover More"
                        )
                    }
                }
                // LazyRow를 활용하여 가로로 스크롤 가능한 영화 목록 표시
                // items는 각 영화 항목에 대해 MovieCoverImage 컴포저블을 호출
                // MovieCoverImage는 영화 포스터 이미지를 표시하는 UI 구성 요소
                LazyRow {
                    items(discoverMovies) {
                        MovieCoverImage(
                            movie = it,
                            onMovieClick = onMovieClick
                        )
                    }
                }

                // Trending Now 섹션 헤더
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = itemSpacing),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = "Trending Now",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { /* TODO: Implement click action */ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = "Trending More"
                        )
                    }
                }
                LazyRow {
                    items(trendingMovies) {
                        MovieCoverImage(
                            movie = it,
                            onMovieClick = onMovieClick
                        )
                    }
                }
            }


        }
    }

}