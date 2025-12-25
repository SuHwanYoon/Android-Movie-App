package com.yoon.openmovie.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yoon.openmovie.common.data.ApiMapper
import com.yoon.openmovie.movie.data.remote.models.MovieDto
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.movie_detail.data.mapper_impl.MovieDetailMapperImpl
import com.yoon.openmovie.movie_detail.data.remote.api.MovieDetailApiService
import com.yoon.openmovie.movie_detail.data.remote.models.MovieDetailDto
import com.yoon.openmovie.movie_detail.data.repo_impl.MovieDetailRepositoryImpl
import com.yoon.openmovie.movie_detail.domain.models.MovieDetail
import com.yoon.openmovie.movie_detail.domain.repository.MovieDetailRepository
import com.yoon.openmovie.utils.K
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * 영화 상세 정보 화면과 관련된 의존성을 제공하는 Dagger Hilt 모듈입니다.
 *
 * 이 모듈은 애플리케이션의 싱글톤 컴포넌트([SingletonComponent])에 설치되어
 * 앱의 수명 주기 동안 유지되는 인스턴스들을 관리합니다.
 * 주로 영화 상세 정보 기능에 특화된 API 서비스, 데이터 매퍼, 그리고 저장소(Repository)의
 * 인스턴스 생성을 담당합니다.
 *
 * 주요 제공 객체:
 * - [MovieDetailApiService]: 영화 상세 정보를 가져오기 위한 Retrofit 서비스 인터페이스.
 * - [ApiMapper]: DTO와 도메인 모델 간의 변환을 담당하는 매퍼.
 * - [MovieDetailRepository]: 데이터 소스(API)와 도메인 로직을 연결하는 저장소 구현체.
 */
@Module
@InstallIn(SingletonComponent::class)
object MovieDetailModule {

    private val json = Json{
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    /**
     * [MovieDetailRepository]의 싱글톤 인스턴스를 제공합니다.
     *
     * 이 함수는 Dagger의 Provider 메서드로 동작하며, 영화 상세 정보에 접근하는 데 필요한
     * 저장소 구현체를 생성합니다. 원격 데이터 계층과 도메인 계층을 연결하기 위해 API 서비스와
     * 데이터 매퍼 같은 필수 의존성을 주입받습니다.
     *
     * @param movieDetailApiService 영화 상세 정보와 관련된 네트워크 요청을 수행하는 Retrofit 서비스 인터페이스.
     * @param mapper [MovieDetailDto](원격 데이터 모델)를 [MovieDetail](도메인 모델)로 변환하는 데 사용되는 매퍼.
     * @param movieMapper [MovieDto](원격 목록 모델)를 [Movie] 리스트(도메인 모델)로 변환하는 데 사용되는 매퍼 (주로 관련 영화나 추천 영화 목록 처리에 사용됨).
     * @return 제공된 의존성들로 구성된 [MovieDetailRepository]의 인스턴스.
     */
    @Provides
    @Singleton
    fun provideMovieDetailRepository(
        movieDetailApiService: MovieDetailApiService,
        mapper: ApiMapper<MovieDetail, MovieDetailDto>,
        movieMapper: ApiMapper<List<Movie>, MovieDto>
    ): MovieDetailRepository = MovieDetailRepositoryImpl(
        movieDetailApiService = movieDetailApiService,
        apiDetailMapper = mapper,
        apiMovieMapper = movieMapper
    )


    /**
     * [MovieDetail]과 [MovieDetailDto] 간의 데이터 변환을 담당하는 매퍼의 싱글톤 인스턴스를 제공합니다.
     *
     * 이 함수는 [ApiMapper] 인터페이스의 구현체인 [MovieDetailMapperImpl]을 반환합니다.
     * 이 매퍼는 네트워크 계층에서 받은 영화 상세 데이터([MovieDetailDto])를
     * 도메인 계층에서 사용하는 모델([MovieDetail])로 변환하는 역할을 수행합니다.
     *
     * @return [MovieDetail] 도메인 모델과 [MovieDetailDto] 데이터 모델 간의 변환을 처리하는 [ApiMapper] 인스턴스.
     */
    @Provides
    @Singleton
    fun provideMovieDetailMapper(): ApiMapper<MovieDetail, MovieDetailDto>
    = MovieDetailMapperImpl()

    /**
     * [MovieDetailApiService]의 싱글톤 인스턴스를 제공합니다.
     *
     * 이 함수는 Retrofit 빌더를 구성하여 네트워크 요청을 처리하는 API 서비스 인터페이스를 생성합니다.
     * JSON 직렬화/역직렬화를 위한 컨버터 팩토리와 기본 URL 설정을 포함합니다.
     *
     * @return 영화 상세 정보 API 엔드포인트와 통신하기 위해 생성된 [MovieDetailApiService] 인스턴스.
     */
    @Provides
    @Singleton
    fun provideMovieDetailApiService(): MovieDetailApiService {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(MovieDetailApiService::class.java)
    }



}