package com.yoon.openmovie.movie.domain.models

// Movie 도메인 모델은 앱내부에서 사용되는 객체
// UI에서 사용할것이기 때문에 non-nullable로 정의
// 배경 이미지 경로, 장르 ID 목록, 영화 ID,
// 원래 언어 및 제목, 개요, 인기 지수, 포스터 이미지 경로,
// 출시 날짜, 제목, 평균 평점, 투표 수 및 비디오 여부를 포함합니다.
data class Movie(
    val backdropPath: String,
    val genreIds: List<String>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val video: Boolean,
)
