package com.yoon.openmovie.movie_detail.domain.models


/**
 * 영화의 상세 정보를 나타내는 데이터 클래스입니다.
 *
 * 이 클래스는 TMDB API 등의 영화 데이터 소스로부터 가져온 개별 영화에 대한
 * 포괄적인 정보를 담고 있습니다.
 *
 * @property backdropPath 영화의 배경 이미지(Backdrop) 경로 URL
 * @property genreIds 영화가 속한 장르의 ID 목록
 * @property id 영화의 고유 식별자 (ID)
 * @property originalLanguage 영화의 원본 언어 코드 (예: "en", "ko")
 * @property originalTitle 영화의 원본 제목
 * @property overview 영화의 줄거리 및 요약
 * @property popularity 영화의 인기도 점수
 * @property posterPath 영화 포스터 이미지 경로 URL
 * @property releaseDate 영화의 개봉일 (형식: "YYYY-MM-DD")
 * @property title 영화의 제목 (번역된 제목일 수 있음)
 * @property voteAverage 영화의 평균 평점
 * @property voteCount 영화에 대한 투표(평가) 수
 * @property video 비디오(예고편 등) 존재 여부
 * @property cast 영화에 출연한 주요 배우들의 목록 ([Cast] 객체 리스트)
 * @property language 영화에서 사용된 언어 목록
 * @property productionCountry 영화가 제작된 국가 목록
 * @property reviews 영화에 대한 사용자 리뷰 목록 ([Review] 객체 리스트)
 * @property runTime 영화의 상영 시간
 */
data class MovieDetail(
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
    val cast: List<Cast>,
    val language: List<String>,
    val productionCountry: List<String>,
    val reviews: List<Review>,
    val runTime: String
)

/**
 * 영화의 출연진(배우) 정보를 나타내는 데이터 클래스입니다.
 *
 * @property id 출연진의 고유 식별자 (ID)
 * @property name 출연진의 전체 이름 (예: "Tom Hanks")
 * @property genderRole 출연진의 성별 또는 역할 구분
 * @property character 영화에서 맡은 배역 이름
 * @property profilePath 출연진의 프로필 이미지 경로 (이미지가 없을 경우 null)
 * @property firstName 이름의 첫 번째 부분 (이름)
 * @property lastName 이름의 두 번째 부분 (성)
 */
data class Cast(
    val id: Int,
    val name: String,
    val genderRole: String,
    val character: String,
    val profilePath: String?,
) {
    //
    private val nameParts = name.split(" ", limit = 2)
    val firstName = nameParts.getOrNull(0) ?: ""
    val lastName = nameParts.getOrNull(1) ?: ""
}

/**
 * Review는 영화에 대한 사용자 리뷰 정보를 나타내는 데이터 클래스입니다.
 *
 * @property author 리뷰 작성자의 이름입니다.
 * @property content 리뷰의 본문 내용입니다.
 * @property id 리뷰의 고유 식별자입니다.
 * @property createdAt 리뷰가 작성된 날짜 및 시간입니다.
 * @property rating 리뷰 작성자가 부여한 평점입니다.
 */
data class Review(
    val author: String,
    val content: String,
    val id: String,
    val createdAt: String,
    val rating:Double
)