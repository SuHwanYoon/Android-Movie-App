package com.yoon.openmovie.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.yoon.openmovie.ui.detail.MovieDetailScreen
import com.yoon.openmovie.ui.home.HomeScreen
import com.yoon.openmovie.utils.K

/**
 * 영화 애플리케이션의 주요 네비게이션 그래프를 정의합니다.
 *
 * 이 Composable은 앱 내 화면 간 이동을 관리하는 `NavHost`를 설정합니다.
 * 다음과 같은 화면들의 경로(route), 인자(argument), 그리고 화면 전환 애니메이션을 정의합니다:
 *
 * - **홈 화면 (HomeScreen)**: 앱의 시작 목적지로, 영화 목록을 표시합니다. 페이드(Fade) 및 스케일(Scale) 애니메이션이 적용되어 있습니다.
 * - **영화 상세 화면 (FilmScreen)**: 특정 영화의 ID를 전달받아 상세 정보를 표시합니다. 관련 영화 클릭 시 재진입할 수 있습니다.
 *
 * @param modifier NavHost 컨테이너에 적용할 수식어입니다. 기본값은 [Modifier]입니다.
 * @param navController 앱 네비게이션 및 백 스택 관리를 담당하는 [NavHostController]입니다.
 */
@Composable
fun MovieNavigationGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Route.HomeScreen().route,
        modifier = modifier.fillMaxSize()
    ) {
        // composable 함수는 각 화면을 정의합니다.
        // 여기서는 HomeScreen을 정의하고, 화면 전환 애니메이션을 설정합니다.
        // fadeIn과 scaleIn 애니메이션을 사용하여 화면이 나타날 때의 효과를 지정하고,
        // fadeOut과 shrinkOut 애니메이션을 사용하여 화면이 사라질 때의 효과를 지정합니다.
        composable(
            route = Route.HomeScreen().route,
            enterTransition = { fadeIn() + scaleIn() },
            exitTransition = { fadeOut() + shrinkOut() }
        ) {
            // HomeScreen을 표시합니다.
            // onMovieClick 콜백을 통해 영화 클릭 시 FilmScreen으로 네비게이션합니다.
            // navController.navigate()를 사용하여 FilmScreen으로 이동하며,
            // launchSingleTop 옵션을 설정하여 중복된 화면 생성을 방지합니다.
            // popUpTo를 사용하여 네비게이션 스택을 관리합니다.
            // 여기서는 HomeScreen에서 FilmScreen으로 이동할 때,
            // HomeScreen을 스택에 남겨두도록 설정합니다.
            // 이를 통해 뒤로 가기 버튼을 눌렀을 때 HomeScreen으로 돌아올 수 있습니다.
            // 영화 ID는 Route.FilmScreen().getRouteWithArgs(movieId) 메서드를 통해 동적으로 생성됩니다.
            // 이를 통해 특정 영화의 상세 정보를 표시할 수 있습니다.
            HomeScreen(
                onMovieClick = {
                    navController.navigate(
                        Route.FilmScreen().getRouteWithArgs(movieId = it)
                    ) {
                        launchSingleTop = true
                        popUpTo(navController.graph.findStartDestination().id) { inclusive = false }
                    }
                }
            )
        }

        // FilmScreen을 정의합니다.
        // 이 화면은 영화 ID를 인자로 받습니다.
        // navArgument를 사용하여 K.MOVIE_ID라는 이름의 정수형 인자를 정의합니다.
        composable(
            route = Route.FilmScreen().routeWithArgs,
            arguments = listOf(navArgument(name = K.MOVIE_ID) { type = NavType.IntType })
        ) {
            // MovieDetailScreen을 표시합니다.
            // onNavigateUp 콜백을 통해 뒤로 가기 동작을 처리합니다.
            // onMovieClick 콜백을 통해 관련 영화 클릭 시 다시 FilmScreen으로 네비게이션합니다.
            // onActorClick 콜백은 현재 빈 구현으로 남겨두었습니다.
            MovieDetailScreen(
                onNavigateUp = { navController.navigateUp() },
                onMovieClick = {
                    navController
                        .navigate(Route.FilmScreen().getRouteWithArgs(movieId = it)) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = false
                            }
                        }
                },
                onActorClick = {}
            )
        }
    }
}