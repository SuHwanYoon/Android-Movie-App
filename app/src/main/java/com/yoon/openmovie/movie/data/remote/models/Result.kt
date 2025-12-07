package com.yoon.openmovie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Result 데이터 전송 객체는 개별 영화의 세부 정보를 나타냅니다.
// 성인 영화 여부, 배경 이미지 경로, 장르 ID 목록, 영화 ID,
// 원래 언어 및 제목, 개요, 인기 지수, 포스터 이미지 경로,
// 출시 날짜, 제목, 비디오 여부, 평균 평점 및 투표 수를 포함합니다.
// 각 속성은 JSON 키와 매핑되며, null 가능성을 처리합니다.
@Serializable
data class Result(
    @SerialName("adult")
    val adult: Boolean? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("genre_ids")
    val genreIds: List<Int?>? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("original_language")
    val originalLanguage: String? = null,
    @SerialName("original_title")
    val originalTitle: String? = null,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("popularity")
    val popularity: Double? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("video")
    val video: Boolean? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null,
    @SerialName("vote_count")
    val voteCount: Int? = null
)