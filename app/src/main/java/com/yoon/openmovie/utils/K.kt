package com.yoon.openmovie.utils
// object 키워드는 Kotlin에서 싱글톤 객체를 정의하는 데 사용됩니다.
// TMDb (The Movie Database) API의 기본 URL, 이미지 URL,
// 다양한 API 엔드포인트와 같은 상수들을 중앙에서 관리하여 API 설정의 접근성과 수정 용이성을 높입니다.
object K {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    const val MOVIE_ENDPOINT ="discover/movie"
    const val MOVIE_DETAIL_ENDPOINT ="movie"
    const val MOVIE_ACTOR_ENDPOINT ="person"
    const val TRENDING_MOVIE_ENDPOINT ="trending/movie/week"
    const val MOVIE_ID ="id"
    const val ACTOR_ID ="id"
}