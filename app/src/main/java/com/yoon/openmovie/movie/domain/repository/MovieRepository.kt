package com.yoon.openmovie.movie.domain.repository

import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.utils.Response
import kotlinx.coroutines.flow.Flow

// MovieRepository 인터페이스는 영화 관련 데이터 작업을 정의합니다.
// fetchDiscoverMovie 메서드는 발견된 영화 목록을 비동기적으로 가져오는 기능을 제공합니다.
// fetchTrendingMovie 메서드는 인기 영화 목록을 비동기적으로 가져오는 기능을 제공합니다.
interface MovieRepository {
    // Flow는 Kotlin 코루틴의 일부로, 비동기 데이터 스트림을 처리하는 데 사용됩니다.
    // Flow<Response<List<Movie>>>는 비동기 데이터 스트림을 나타내며,
    // Response는 데이터의 상태(성공, 오류 등)를 캡슐화합니다.
    fun fetchDiscoverMovie(): Flow<Response<List<Movie>>>
    fun fetchTrendingMovie(): Flow<Response<List<Movie>>>
}