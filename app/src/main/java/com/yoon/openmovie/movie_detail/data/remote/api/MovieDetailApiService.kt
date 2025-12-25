package com.yoon.openmovie.movie_detail.data.remote.api

import com.yoon.openmovie.BuildConfig
import com.yoon.openmovie.movie.data.remote.models.MovieDto
import com.yoon.openmovie.movie_detail.data.remote.models.MovieDetailDto
import com.yoon.openmovie.utils.K
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val MOVIE_ID = "movie_id"


/**
 * 원격 API로부터 영화 상세 정보 및 관련 영화 데이터를 가져오기 위한 Retrofit 서비스 인터페이스입니다.
 *
 * 이 서비스는 특정 영화에 대한 세부 메타데이터 조회와 일반적인 영화 목록 데이터를 요청하는 API 호출을 처리합니다.
 */
interface MovieDetailApiService {


    /**
     * API에서 특정 영화의 상세 정보를 가져옵니다.
     *
     * 이 함수는 영화 상세 정보 엔드포인트에 GET 요청을 보냅니다. 기본적으로 크레딧(credits)과 리뷰(reviews) 정보를
     * 응답에 포함하도록 설정되어 있습니다.
     *
     * @param movieId 조회할 영화의 고유 식별자(ID).
     * @param apiKey 인증을 위한 API 키. 기본값은 [BuildConfig.apiKey]입니다.
     * @param appendToResponse 응답에 추가로 포함할 데이터들을 쉼표로 구분한 문자열 (예: "credits,reviews"). 기본값은 "credits,reviews"입니다.
     * @return 추가 데이터를 포함한 영화의 상세 정보를 담은 [MovieDetailDto] 객체.
     */
    @GET("${K.MOVIE_DETAIL_ENDPOINT}/{$MOVIE_ID}")
    suspend fun fetchMovieDetail(
        @Path(MOVIE_ID) movieId : Int,
        @Query("api_key") apiKey : String = BuildConfig.apiKey,
        @Query("append_to_response") appendToResponse : String = "credits,reviews"
    ): MovieDetailDto

    @GET(K.MOVIE_ENDPOINT)
    suspend fun fetchMovie(
        @Query("api_key") apiKey: String = BuildConfig.apiKey,
        @Query("include_adult") includeAdult: Boolean = false,
    ): MovieDto
}