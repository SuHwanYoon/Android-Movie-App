package com.yoon.openmovie.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.yoon.openmovie.common.data.ApiMapper
import com.yoon.openmovie.movie.data.mapper_impl.MovieApiMapperImpl
import com.yoon.openmovie.movie.data.remote.api.MovieApiService
import com.yoon.openmovie.movie.data.remote.models.MovieDto
import com.yoon.openmovie.movie.data.repository_impl.MovieRepositoryImpl
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.movie.domain.repository.MovieRepository
import com.yoon.openmovie.utils.K
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

// MovieModule 객체는 하나만 있으면 충분한 객체이기에
// object 키워드를 사용하여 싱글톤으로 정의됩니다.
// @Module 어노테이션은 이 객체가 Dagger 모듈임을 나타냅니다.
// @InstallIn(SingletonComponent::class) 어노테이션은
// 이 모듈이 애플리케이션 전체에 걸쳐 싱글톤 범위로 제공됨을 지정합니다.
@Module
@InstallIn(SingletonComponent::class)
object MovieModule {

    // json 프로퍼티는 Json 객체를 초기화합니다.
    // coerceInputValues = true 설정은
    // 입력 값이 예상된 유형과 일치하지 않을 때 자동으로 강제 변환을 수행합니다.
    // ignoreUnknownKeys = true 설정은
    // JSON 데이터에 매핑되지 않은 추가 키가 있을 때 무시하도록 지정합니다
    private val json = Json{
        coerceInputValues = true
        ignoreUnknownKeys = true
    }


    // @Provides 어노테이션은
    // 이 함수가 Dagger에 의해 제공되는 종속성을 생성하는 데 사용됨을 나타냅니다.
    // @Singleton 어노테이션은
    // 이 제공된 종속성이 애플리케이션 전체에서 단일 인스턴스로 유지됨을 보장합니다.
    // provideMovieRepository 함수는 MovieRepository 인터페이스의 구현체를 제공합니다.
    // = 는 {} + return 구문을 생략한 표현식 본문 함수입니다.
    @Provides
    @Singleton
    fun provideMovieRepository(
       movieApiService: MovieApiService,
       mapper: ApiMapper<List<Movie>, MovieDto>
    ): MovieRepository
    = MovieRepositoryImpl(movieApiService , mapper)

    // provideMovieMapper 함수는
    // ApiMapper 인터페이스의 구현체를 제공합니다.
    @Provides
    @Singleton
    fun provideMovieMapper(): ApiMapper<List<Movie>, MovieDto>
    = MovieApiMapperImpl()

    @Provides
    @Singleton
    // provideMovieApiService 함수는 MovieApiService 인터페이스의 구현체를 제공합니다.
    fun provideMovieApiService(): MovieApiService{
        // contentType 변수는 미디어 유형을 나타내며,
        // "application/json" 문자열을 MediaType 객체로 변환합니다.
        val contentType = "application/json".toMediaType()
        // Retrofit.Builder()를 사용하여 Retrofit 인스턴스를 생성하고 구성합니다.
        // baseUrl() 메서드는 API의 기본 URL을 설정합니다.
        // addConverterFactory() 메서드는 JSON 변환을 처리할 컨버터 팩토리를 추가합니다.
        // build() 메서드는 구성된 Retrofit 인스턴스를 생성합니다.
        // create(MovieApiService::class.java) 메서드는
        // 직접 구현 클래스를 만들지 않아도
        // Retrofit이 MovieApiService 인터페이스를 기반으로
        // 런타임에 구현체를 생성하여 반환한다
        // 구체적으로는 앱에서 영화 목록을 가져오는 시점에 생성된다고 보면 된다
        return Retrofit.Builder()
            .baseUrl(K.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(MovieApiService::class.java)
    }


}