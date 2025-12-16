package com.yoon.openmovie.ui.home


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.ui.components.LoadingView
import com.yoon.openmovie.ui.home.component.BodyContent
import com.yoon.openmovie.ui.home.component.TopContent
import kotlinx.coroutines.delay

// UI 구성에 사용되는 기본 패딩과 항목 간격을 정의
// dp는 밀도 독립 픽셀 단위로, 다양한 화면 밀도에서 일관된 크기를 유지
// 16.dp는 16밀도 독립 픽셀을 의미
// 8.dp는 8밀도 독립 픽셀을 의미
// 이러한 값들은 UI 요소들 간의 간격과 여백을 설정하는 데 사용
// 예: 기본 패딩은 화면의 가장자리와 콘텐츠 사이의 여백을 설정
// 예: 항목 간격은 리스트나 그리드 항목들 사이의 간격을 설정
val defaultPadding = 16.dp
val itemSpacing = 8.dp

// HomeScreen 컴포저블 함수는 홈 화면 UI를 구성합니다.
// modifier 매개변수는 UI 요소의 레이아웃과 스타일을 조정하는 데 사용됩니다.
// homeViewModel 매개변수는 Hilt를 사용하여 주입된 HomeViewModel 인스턴스를 나타냅니다.
// onMovieClick 매개변수는 영화 항목이 클릭될 때 호출되는 람다 함수입니다.
@Composable
fun HomeScreen(
    //기본값 Modifier를 사용하여 호출 시 HomeScreen()의 형태로 호출이 가능
    // 또한 호출시 () 내에서 modifier를 전달하여 메서드를 통해 커스터마이징도 가능
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (id: Int) -> Unit,
) {
    // isAutoScrolling 상태 변수는 자동 스크롤링 기능의 활성화 여부를 나타내는 반응형 데이터
    // 사용자의 동작 또는 로직에 따라 값이 바뀌어야 하는 상태는 var로 선언
    // by 키워드는 상태 변수를 읽고 쓸 때 자동으로 getValue와 setValue를 호출하도록 도와줍니다.
    // 즉 .value를 사용하지 않고도  상태 변수에 접근할 수 있습니다.
    // remember 함수는 컴포저블 함수가 재구성될 때 상태 변화를 유지
    // mutableStateOf는 상태 변수를 생성하고 = true, = flase로 할당 가능하게 함
    var isAutoScrolling by remember {
        mutableStateOf(true)
    }
    // state 변수는 HomeViewModel의 homeState를 수집하여
    // Compose의 수명 주기에 따라 상태 변화를 반영하는 반응형 데이터
    // collectAsStateWithLifecycle 함수는 ViewModel의 StateFlow를
    // Compose에서 관찰가능하게 하고 화면이 보여지고 있을때만 수집해서 불필요한 요청이나 성능낭비를 막음
    val state by homeViewModel.homeState.collectAsStateWithLifecycle()

    // pagerState 변수는 수평 페이저 컴포넌트의 상태를 관리하는 객체
    // rememberPagerState 함수는 페이저의 현재 페이지와 총 페이지 수를 기억
    // initialPage는 페이저가 처음 시작할 페이지를 지정
    // pageCount는 페이저에 표시될 총 페이지 수를 지정
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.discoverMovies.size }
    )

    // isDragged 변수는 사용자가 페이저를 드래그하고 있는지 여부를 나타내는 반응형 데이터
    // pagerState의 interactionSource를 사용하여 페이저의 상호작용 상태를 수집
    // collectIsDraggedAsState 함수는 사용자가 페이저를 드래그하고 있는지 여부를 true/false로 반환
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    // LaunchedEffect는 컴포저블 함수 내에서 코루틴을 실행할 수 있는 효과
    // key1 파라미터는 이 효과가 재실행될 조건을 지정
    // 여기서는 pagerState.currentPage가 변경될 때마다 효과가 재실행
    LaunchedEffect(key1 = pagerState.currentPage) {
        // 자동 스크롤링이 비활성화된 경우 효과를 종료
        if (isDragged) {
            isAutoScrolling = false
        } else {
            // 자동 스크롤링이 활성화된 경우 5초 대기 후 다음 페이지로 스크롤
            // isAutoScrolling 값이 true인 동안에만 자동 스크롤링 동작 수행
            // 5초(5000밀리초) 대기
            // with 블록은 특정 객체(pagerState)의 컨텍스트 내에서 코드를 실행할 수 있도록 함
            // pagerState를 사용하여 현재 페이지를 확인하고,
            // 다음 페이지로 스크롤하거나 첫 페이지로 돌아감
            // isAutoScrolling 값이 false로 변경되면 이 블록이 실행되지 않음
            isAutoScrolling = true
            delay(5000)
            with(pagerState) {
                val target = if (currentPage < state.discoverMovies.size - 1) {
                    currentPage + 1
                } else {
                    0
                }
                // animateScrollToPage 함수를 사용하여 페이저를 스크롤
                animateScrollToPage(target)
            }
        }
    }
    // 오류 메시지를 표시하는 Box
    // AnimatedVisibility 컴포저블은 애니메이션과 함께
    // 콘텐츠의 가시성을 제어하는 데 사용됩니다.
    // state.error가 null이 아니면 오류 메시지를 표시
    Box(modifier = Modifier) {
        AnimatedVisibility(visible = state.error != null) {
            Text(
                text = state.error ?: "Unknown Error",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
                maxLines = 2
            )
        }
        // 로딩 중인 상태를 처리하는 AnimatedVisibility 컴포저블
        // state.isLoading이 false이고 state.error가 null인 경우에만
        // 내부 콘텐츠가 표시됩니다.
        AnimatedVisibility(visible = !state.isLoading && state.error == null) {
            // Column을 사용하여 TopContent와 BodyContent를 수직으로 배치
            // fillMaxSize()로 전체 화면을 차지하도록 설정
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val boxHeight = this.maxHeight
                val topItemHeight = boxHeight * .45f
                val bodyItemHeight = boxHeight * .55f
                // HorizontalPager 사용하여 영화 항목을 가로로 스크롤할 수 있는 UI 구성
                //  pagerState를 전달하여 페이저의 상태를 관리
                // contentPadding은 페이저의 콘텐츠에 대한 패딩을 지정
                // pageSize는 각 페이지의 크기를 지정
                // pageSpacing은 페이지 간의 간격을 지정
                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(),
                    pageSize = PageSize.Fill,
                    pageSpacing = itemSpacing
                ) {
                    //  page 매개변수는 현재 페이지의 인덱스를 나타냄
                    // isAutoScrolling 값에 따라 다른 콘텐츠를 표시
                    // isAutoScrolling이 true인 경우 TopContent 컴포저블을 표시
                    //  영화 항목이 클릭되면 onMovieClick 람다 함수가 호출
                        page ->
                    if (isAutoScrolling) {
                        AnimatedContent(
                            targetState = page,
                            label = "",
                        ) {
                            // index는 현재 페이지의 인덱스를 나타냄
                            // it는 AnimatedContent의 targetState를 나타내며,
                            // 여기서는 현재 페이지 인덱스를 의미
                            //  Modifier.align(Alignment.TopCenter)를 사용하여
                            // TopContent를 상단 중앙에 정렬
                            // heightIn(min = topItemHeight)를 사용하여
                            // TopContent의 최소 높이를 boxHeight의 45%로 설정
                            // movie 매개변수에는 현재 페이지에 해당하는 영화 데이터가 전달
                            // onMovieClick 람다 함수는 영화 항목이 클릭될 때 호출
                            index ->
                            TopContent(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .heightIn(min = topItemHeight),
                                movie = state.discoverMovies[index],
                                onMovieClick = {
                                    onMovieClick(it)
                                }
                            )

                        }
                        // 자동 스크롤링이 비활성화된 경우 일반 TopContent 표시
                        // movie 매개변수에는 현재 페이지에 해당하는 영화 데이터가 전달
                        // onMovieClick 람다 함수는 영화 항목이 클릭될 때 호출
                    } else {
                        TopContent(
                          modifier = Modifier
                              .align(Alignment.TopCenter)
                              .heightIn(min = topItemHeight),
                            movie = state.discoverMovies[page],
                            onMovieClick = {
                                onMovieClick(it)
                            }
                        )
                    }
                }
                // BodyContent 컴포저블을 사용하여
                // 발견된 영화와 인기 영화를 표시하는 UI 구성
                // Modifier.align(Alignment.BottomCenter)를 사용하여
                // BodyContent를 하단 중앙에 정렬
                // heightIn(max = bodyItemHeight)를 사용하여
                // BodyContent의 최대 높이를 boxHeight의 55%로 설정
                BodyContent(
                  modifier = Modifier
                      .align(Alignment.BottomCenter)
                      .heightIn(max = bodyItemHeight),
                    discoverMovies = state.discoverMovies,
                    trendingMovies = state.trendingMovies,
                    onMovieClick = onMovieClick
                )
            }
        }
    }

    // HomeScreen 컴포저블 함수의 끝
    // Loading 및 오류 상태를 처리하는 LoadingView 컴포저블 호출
    // state.isLoading이 true인 경우 로딩 뷰를 표시
    // LoadingView 컴포저블은 로딩 상태를 시각적으로 나타내는 UI 구성 요소
    // isLoading 매개변수에는 현재 로딩 상태가 전달
    // 예: 로딩 스피너 또는 진행 표시줄
    // 오류 상태는 앞서 AnimatedVisibility에서 처리되었으므로 여기서는 로딩 상태만 처리
    // LoadingView는 화면 전체에 오버레이로 표시될 수 있음
    LoadingView(isLoading = state.isLoading)

}

// preview용 더미데이터
@Preview(showBackground = true)
@Composable
fun getDummyMovies() = List(10) {
    Movie(
        id = it,
        backdropPath = "/path/to/backdrop$it.jpg",
        genreIds = listOf("Action", "Adventure"),
        originalLanguage = "en",
        originalTitle = "Original Title $it",
        overview = "This is a brief overview of movie $it.",
        popularity = 123.45 + it,
        posterPath = "/path/to/poster$it.jpg",
        releaseDate = "2023-10-0${it + 1}",
        title = "Movie Title $it",
        voteAverage = 7.5 + (it % 3),
        voteCount = 1000 + (it * 100),
        video = false
    )
}