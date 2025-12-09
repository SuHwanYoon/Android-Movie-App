package com.yoon.openmovie.movie.data.repository_impl

import com.yoon.openmovie.common.data.ApiMapper
import com.yoon.openmovie.movie.data.remote.api.MovieApiService
import com.yoon.openmovie.movie.data.remote.models.MovieDto
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.movie.domain.repository.MovieRepository
import com.yoon.openmovie.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(
    private val movieApiService: MovieApiService,
    private val apiMapper: ApiMapper<List<Movie>, MovieDto>
) : MovieRepository {
    // flow는 내부에서 emit()를 호출하면서 실제로 값을 흘려보내는 Flow를 만드는  빌더함수
    override fun fetchDiscoverMovie(): Flow<Response<List<Movie>>> = flow {
        // emit는 flow내부에서 값을 방출하는 역할
        // 데이터 로딩 시작을 알리는 로딩 상태를 방출
        // 제네릭 타입이므로 Response.Loading()에 타입 매개변수를 명시하지 않아도 됨
        // 예: Flow<Response<List<Movie>>>이므로 Response.Loading<List<Movie>>로 추론
        emit(Response.Loading())
        val movieDto = movieApiService.fetchDiscoverMovie()
        apiMapper.mapToDomain(movieDto).apply {
            emit(Response.Success(this))
        }
    }.catch {  error ->
        // 에러 발생 시 에러 상태를 방출
        emit(Response.Error(error))
    }

    override fun fetchTrendingMovie(): Flow<Response<List<Movie>>> = flow {
        // emit는 flow내부에서 값을 방출하는 역할
        // 데이터 로딩 시작을 알리는 로딩 상태를 방출
        // 제네릭 타입이므로 Response.Loading()에 타입 매개변수를 명시하지 않아도 됨
        // 예: Flow<Response<List<Movie>>>이므로 Response.Loading<List<Movie>>로 추론
        emit(Response.Loading())
        val movieDto = movieApiService.fetchTrendingMovie()
        apiMapper.mapToDomain(movieDto).apply {
            emit(Response.Success(this))
        }
    }.catch {  error ->
        // 에러 발생 시 에러 상태를 방출
        emit(Response.Error(error))
    }
}