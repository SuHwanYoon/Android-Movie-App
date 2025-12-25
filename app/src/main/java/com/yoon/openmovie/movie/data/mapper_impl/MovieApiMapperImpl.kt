package com.yoon.openmovie.movie.data.mapper_impl

import com.yoon.openmovie.common.data.ApiMapper
import com.yoon.openmovie.movie.data.remote.models.MovieDto
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.utils.GenreConstants

/**
 * [ApiMapper] 인터페이스를 구현하여 [MovieDto]를 도메인 모델인 [List]<[Movie]>로 변환하는 클래스입니다.
 *
 * 이 클래스는 네트워크 응답(DTO)을 UI나 비즈니스 로직에서 사용하기 적합한 형태의 도메인 객체 리스트로 매핑합니다.
 * 주요 기능으로는 null 값에 대한 기본값 처리(fallback) 및 장르 ID를 장르 이름으로 변환하는 작업 등이 포함됩니다.
 */
// MovieApiMapperImpl 클래스는 ApiMapper 인터페이스를 구현하여
// MovieDto를 List<Movie> 도메인 모델로 변환하는 매핑 기능을 제공합니다.
// ApiMapper 인터페이스를 구현하였기 때문에 mapToDomain 메서드를 반드시 구현해야 합니다.
class MovieApiMapperImpl : ApiMapper<List<Movie>, MovieDto> {
    /**
     * API 응답 데이터인 [MovieDto] 객체를 도메인 모델 리스트인 [List]<[Movie]>로 변환합니다.
     *
     * [MovieDto] 내부의 `results` 리스트를 순회하며 각 영화 정보를 [Movie] 객체로 매핑합니다.
     * 이 과정에서 `formatEmptyValue`와 `formatGenre` 등의 보조 함수를 사용하여
     * null이거나 비어있는 값에 대한 예외 처리를 수행하고, 장르 ID를 읽을 수 있는 이름으로 변환합니다.
     *
     * @param apiDto 네트워크 요청을 통해 전달받은 영화 데이터 DTO 객체
     * @return 변환된 [Movie] 도메인 객체들의 리스트. `apiDto.results`가 null인 경우 빈 리스트를 반환합니다.
     */
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