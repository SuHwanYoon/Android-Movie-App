package com.yoon.openmovie.movie.data.mapper_impl

import com.yoon.openmovie.common.data.ApiMapper
import com.yoon.openmovie.movie.data.remote.models.MovieDto
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.utils.GenreConstants

// MovieApiMapperImpl 클래스는 ApiMapper 인터페이스를 구현하여
// MovieDto를 List<Movie> 도메인 모델로 변환하는 매핑 기능을 제공합니다.
// ApiMapper 인터페이스를 구현하였기 때문에 mapToDomain 메서드를 반드시 구현해야 합니다.
class MovieApiMapperImpl : ApiMapper<List<Movie>, MovieDto> {
    // mapToDomain 메서드는 API데이터를 받은 MovieDto 객체를  map을 사용하여
    // 프로퍼티중 하나인 Result타입의 리스트를 순회하고 각 Result 항목요소를 Movie 도메인 모델에 담아
    // Movie객체 타입의 리스트를 반환합니다.
    override fun mapToDomain(apiDto: MovieDto): List<Movie> {
        return apiDto.results?.map { result ->
            Movie(
                backdropPath = formatEmptyValue(result?.backdropPath),
                genreIds = formatGenre(result?.genreIds),
                id = result?.id ?: 0,
                originalLanguage = formatEmptyValue(result?.originalLanguage, "language"),
                originalTitle = formatEmptyValue(result?.originalTitle, "title"),
                overview = formatEmptyValue(result?.overview, "overview"),
                popularity = result?.popularity ?: 0.0,
                posterPath = formatEmptyValue(result?.posterPath),
                releaseDate = formatEmptyValue(result?.releaseDate, "date"),
                title = formatEmptyValue(result?.title, "title"),
                voteAverage = result?.voteAverage ?: 0.0,
                voteCount = result?.voteCount ?: 0,
                video = result?.video ?: false
            )
        } ?: emptyList()
    }


    // mapToDomain 메서드 내부에서 사용되는 보조 함수들

    // formatEmptyValue 함수는 주어진 문자열이 null이거나 비어있는지 확인하고,
    // 그렇다면 "Unknown " 뒤에 기본 설명 텍스트를 붙여 반환합니다
    private fun formatEmptyValue(value: String?, default: String = ""): String {
        if (value.isNullOrEmpty()) return "Unknown $default"
        return value
    }

    // formatGenre 함수는 장르 ID 목록을 받아
    // 각 ID를 해당 장르 이름으로 매핑하여 문자열 목록으로 반환합니다.
    private fun formatGenre(genreIds: List<Int?>?): List<String> {
        return genreIds?.map { GenreConstants.getGenreNameById(it ?: 0) } ?: emptyList()
    }
}