package com.yoon.openmovie.utils

// 영화 장르 ID와 해당 이름을 매핑하는 genreMap을 포함하며,
// ID를 통해 장르 이름을 조회하는 함수를 제공하여 사용자에게 이해하기 쉬운 장르 정보를 표시하는 데 사용됩니다.
// mapOf는 불변 맵을 생성합니다.
// 28 to "Action"는 키-값 쌍을 나타내며, 28이라는 키에 "Action"이라는 값을 매핑합니다.
// to는 Kotlin에서 키-값 쌍을 생성하는 데 사용되는 연산자입니다.
object GenreConstants {
    private val genreMap = mapOf(
        28 to "Action",
        12 to "Adventure",
        16 to "Animation",
        35 to "Comedy",
        80 to "Crime",
        99 to "Documentary",
        18 to "Drama",
        10751 to "Family",
        14 to "Fantasy",
        36 to "History",
        27 to "Horror",
        10402 to "Music",
        9648 to "Mystery",
        10749 to "Romance",
        878 to "Science Fiction",
        10770 to "TV Movie",
        53 to "Thriller",
        10752 to "War",
        37 to "Western"
    )

    fun getGenreNameById(id: Int): String {
        return genreMap[id] ?: "Unknown"
    }
}
