package com.yoon.openmovie.movie_detail.domain.repository

import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.movie_detail.domain.models.MovieDetail
import com.yoon.openmovie.utils.Response
import kotlinx.coroutines.flow.Flow


// MovieDetailRepository 인터페이스는 영화 상세 정보 관련 데이터 작업을 정의합니다.
// fetchMovieDetail 메서드는 영화 상세 정보를 비동기적으로 가져오는 기능을 제공합니다.
// fetchMovie() 함수는 영화 목록을 비동기적으로 가져오는 기능을 제공합니다.
interface MovieDetailRepository {
    fun fetchMovieDetail(movieId: Int): Flow<Response<MovieDetail>>

    fun fetchMovie(): Flow<Response<List<Movie>>>
}