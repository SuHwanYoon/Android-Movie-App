package com.yoon.openmovie.ui.navigation

import com.yoon.openmovie.utils.K

/**
 * 애플리케이션의 네비게이션 경로(Route)를 정의하는 Sealed Class입니다.
 *
 * 이 클래스는 앱 내에서 이동 가능한 화면들의 계층 구조를 정의하며,
 * 고정된 경로 문자열과 인자가 포함된 동적 경로를 모두 캡슐화합니다.
 *
 * @property HomeScreen 앱의 메인 랜딩 화면을 나타냅니다. 기본 경로는 "home_screen"입니다.
 * @property FilmScreen 특정 영화의 상세 정보를 보여주는 화면을 나타냅니다.
 *           영화 ID를 인자로 받아 동적인 경로를 생성할 수 있습니다.
 */
sealed class Route{
    data class HomeScreen(val route: String = "home_screen"): Route()
    data class FilmScreen(
        val route: String = "film_screen",
        val routeWithArgs: String = "$route/{${K.MOVIE_ID}}"
    ) : Route(){
        fun getRouteWithArgs(movieId: Int): String{
            return "$route/$movieId"
        }
    }
}