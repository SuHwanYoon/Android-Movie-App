package com.yoon.openmovie.movie_detail.domain.repository

import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.movie_detail.domain.models.MovieDetail
import com.yoon.openmovie.utils.Response
import kotlinx.coroutines.flow.Flow


/**
 * MovieDetailRepository 인터페이스는 영화 상세 정보 및 영화 목록 관련 데이터 작업을 정의합니다.
 *
 * 이 리포지토리는 애플리케이션의 도메인 레이어에 위치하며, 데이터 소스(네트워크, 로컬 DB 등)로부터
 * 영화 데이터를 비동기적으로 가져오는 계약(Contract)을 명시합니다.
 */
// MovieDetailRepository 인터페이스는 영화 상세 정보 관련 데이터 작업을 정의합니다.
// fetchMovieDetail 메서드는 영화 상세 정보를 비동기적으로 가져오는 기능을 제공합니다.
// fetchMovie() 함수는 영화 목록을 비동기적으로 가져오는 기능을 제공합니다.
interface MovieDetailRepository {
    /**
     * 지정된 영화 ID에 해당하는 영화의 상세 정보를 비동기적으로 가져옵니다.
     *
     * 이 함수는 네트워크 또는 로컬 데이터 소스로부터 영화 상세 데이터를 요청하며,
     * 작업의 성공 또는 실패 상태를 포함하는 [Response] 객체를 [Flow]로 방출합니다.
     *
     * @param movieId 조회할 영화의 고유 식별자 (ID).
     * @return [MovieDetail] 객체를 포함하는 [Response]의 [Flow].
     *         데이터 로딩 중, 성공 시 데이터, 또는 에러 발생 시 예외 정보를 순차적으로 전달할 수 있습니다.
     */
    fun fetchMovieDetail(movieId: Int): Flow<Response<MovieDetail>>

    /**
     * 영화 목록을 비동기적으로 가져옵니다.
     *
     * 이 함수는 네트워크 API 또는 로컬 데이터 소스를 통해 영화 리스트를 요청하며,
     * 로딩 상태, 성공 시의 영화 목록 데이터, 또는 에러 발생 시의 예외 정보를 포함하는
     * [Response] 객체를 [Flow] 스트림으로 방출합니다.
     *
     * @return [Movie] 리스트를 포함하는 [Response]의 [Flow].
     */
    fun fetchMovie(): Flow<Response<List<Movie>>>
}