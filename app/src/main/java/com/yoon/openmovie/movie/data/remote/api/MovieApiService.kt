package com.yoon.openmovie.movie.data.remote.api

import com.yoon.openmovie.BuildConfig
import com.yoon.openmovie.movie.data.remote.models.MovieDto
import com.yoon.openmovie.utils.K
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 영화 관련 API 호출을 정의하는 Retrofit 서비스 인터페이스입니다.
 *
 * 이 인터페이스는 TMDb (The Movie Database) API와 통신하여 영화 데이터를 가져오는 데 사용됩니다.
 * 모든 네트워크 요청은 Coroutine을 지원하기 위해 `suspend` 함수로 정의되어 있습니다.
 *
 * 주요 기능:
 * - [fetchDiscoverMovie]: 탐색(Discover) 엔드포인트를 통해 영화 목록을 조회합니다.
 * - [fetchTrendingMovie]: 주간 트렌딩 영화 목록을 조회합니다.
 *
 * @see com.yoon.openmovie.utils.K API 엔드포인트 상수가 정의된 객체
 * @see com.yoon.openmovie.movie.data.remote.models.MovieDto API 응답 데이터 모델
 */
interface MovieApiService {
    // suspend 키워드는 이 함수가 코루틴 내에서 비동기적으로 호출될 수 있음을 나타냅니다.
    // fetchDiscoverMovie 함수는 "discover/movie" 엔드포인트에서 영화 데이터를 비동기적으로 가져옵니다.
    @GET(K.MOVIE_ENDPOINT)
    suspend fun fetchDiscoverMovie(
        // @Query 어노테이션은 함수 매개변수를 쿼리 매개변수로 매핑하는 데 사용됩니다.
        // BuildConfig.apiKey는 빌드 구성에서 API 키를 가져옵니다.
        // URL에 포함될 쿼리 매개변수의 이름은 "api_key"입니다.
        // api_key = apikey value 형식으로 요청 URL에 추가됩니다.
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
        @Query("include_adult") includeAdult: Boolean = false,
    ): MovieDto

    // fetchTrendingMovie 함수는 "trending/movie/week"
    // 엔드포인트에서 주간 인기 영화 데이터를 비동기적으로 가져옵니다.
    @GET(K.TRENDING_MOVIE_ENDPOINT)
    suspend fun fetchTrendingMovie(
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
        @Query("include_adult") includeAdult: Boolean = false,
    ): MovieDto
}