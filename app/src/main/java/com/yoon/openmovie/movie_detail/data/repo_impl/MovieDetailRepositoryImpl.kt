package com.yoon.openmovie.movie_detail.data.repo_impl

import com.yoon.openmovie.common.data.ApiMapper
import com.yoon.openmovie.movie.data.remote.models.MovieDto
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.movie_detail.data.remote.api.MovieDetailApiService
import com.yoon.openmovie.movie_detail.data.remote.models.MovieDetailDto
import com.yoon.openmovie.movie_detail.domain.models.MovieDetail
import com.yoon.openmovie.movie_detail.domain.repository.MovieDetailRepository
import com.yoon.openmovie.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * [MovieDetailRepository] 인터페이스의 구현체입니다.
 *
 * 이 클래스는 데이터 계층(Data Layer)에서 영화 상세 정보를 관리하는 저장소 역할을 수행합니다.
 * [MovieDetailApiService]를 통해 원격 서버로부터 데이터를 가져오고,
 * [ApiMapper]를 사용하여 DTO(Data Transfer Object)를 도메인 모델로 변환하여 제공합니다.
 *
 * 주요 기능:
 * - 영화 상세 정보 조회 ([fetchMovieDetail])
 * - (구현 예정) 영화 목록 조회 ([fetchMovie])
 *
 * @property movieDetailApiService 영화 상세 정보 관련 API 호출을 담당하는 서비스.
 * @property apiDetailMapper [MovieDetailDto]를 [MovieDetail] 도메인 모델로 변환하는 매퍼.
 * @property apiMovieMapper [MovieDto]를 [Movie] 리스트 도메인 모델로 변환하는 매퍼.
 */
class MovieDetailRepositoryImpl(
    private val movieDetailApiService: MovieDetailApiService,
    private val apiDetailMapper: ApiMapper<MovieDetail, MovieDetailDto>,
    private val apiMovieMapper: ApiMapper<List<Movie>, MovieDto>
) : MovieDetailRepository {

    /**
     * 특정 영화의 상세 정보를 가져옵니다.
     *
     * 이 함수는 [MovieDetailApiService]를 통해 네트워크 요청을 수행하고,
     * 응답으로 받은 [MovieDetailDto]를 도메인 모델인 [MovieDetail]로 변환하여 [Flow] 형태로 방출합니다.
     *
     * Flow는 다음과 같은 순서로 데이터를 방출합니다:
     * 1. [Response.Loading]: 작업이 시작되었음을 알리기 위해 즉시 방출됩니다.
     * 2. [Response.Success]: API 호출이 성공하면 변환된 [MovieDetail] 데이터를 포함하여 방출됩니다.
     *
     * @param movieId 조회할 영화의 고유 ID.
     * @return [MovieDetail] 상태를 포함하는 [Response] 래퍼를 방출하는 [Flow].

     */
    override fun fetchMovieDetail(movieId: Int): Flow<Response<MovieDetail>> = flow {
        emit(Response.Loading())
        val movieDetailDto = movieDetailApiService.fetchMovieDetail(movieId)
        apiDetailMapper.mapToDomain(movieDetailDto).apply {
            emit(Response.Success(this))
        }
    }.catch { error ->
        error.printStackTrace()
        emit(Response.Error(error))
    }

    /**
     * 영화 목록을 가져옵니다.
     *
     * 이 함수는 [MovieDetailApiService]를 통해 네트워크 요청을 수행하고,
     * 응답으로 받은 [MovieDto]를 도메인 모델 리스트인 [List<Movie>]로 변환하여 [Flow] 형태로 방출합니다.
     *
     * Flow는 다음과 같은 순서로 데이터를 방출합니다:
     * 1. [Response.Loading]: 작업이 시작되었음을 알리기 위해 즉시 방출됩니다.
     * 2. [Response.Success]: API 호출이 성공하면 변환된 [List<Movie>] 데이터를 포함하여 방출됩니다.
     *
     * 참고: 현재 구현 내용이 불완전해 보이지만, 일반적인 Repository 패턴에 따라 작성되었습니다.
     *
     * @return [List<Movie>] 상태를 포함하는 [Response] 래퍼를 방출하는 [Flow].
     */
    override fun fetchMovie(): Flow<Response<List<Movie>>> = flow {
        emit(Response.Loading())
        val movieDto = movieDetailApiService.fetchMovie()
        apiMovieMapper.mapToDomain(movieDto).apply {
            emit(Response.Success(this))
        }
    }.catch { error ->
        error.printStackTrace()
        emit(Response.Error(error))
    }

}