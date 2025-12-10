package com.yoon.openmovie.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.movie.domain.repository.MovieRepository
import com.yoon.openmovie.utils.collectAndHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel을 상속하면 안드로이드 아키텍처 컴포넌트의
// ViewModel 기능을 사용할 수 있으며 기능으로는
// 화면 회전 등의 구성 변경 시에도 데이터 유지,
// UI state를 관찰하며 자동으로 화면갱신
// 코루틴 사용해 API 요청수행 -> 결과만 UI에 전달
// DI구성가능
// @Inject 생성자는 constructor내부에 선언한 의존성을
// ViewModel의 인스턴스를 생성할 때 내부에 명시하지 않아도
// Hilt가 자동으로 주입해줌
// constructor() 는 기본 생성자를 정의하며,
// 필요한 의존성을 매개변수로 받을 수 있습니다.
// ViewModel은 구현체가 아닌 Interface 타입으로 의존성을 주입받는 것이
// 더 좋은 설계입니다.
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
) : ViewModel() {
    // MutableStateFlow는 가변 상태 흐름을 나타내며,
    // StateFlow는 읽기 전용 상태 흐름을 나타냅니다.
    // _homeState는 ViewModel 내부에서 상태를 변경할 수 있는
    // MutableStateFlow로 선언되어 있습니다.
    // homeState는 외부에서 읽기 전용으로 접근할 수 있는
    // StateFlow로 제공됩니다.

    // _homeState는 상태를 실제로 수정하는 주체
    // homeState는 읽기만 가능한 UI 공개용 상태 스트림
    // 이유는 UI가 상태변경을 직접 수행하지 못하게 하기 위함
    private  val _homeState = MutableStateFlow(HomeState())
    val homeState = _homeState.asStateFlow()

    // ViewModel이 생성될 때 함수가 호출되도록 초기화선언
    // 여기서는 홈화면 초기화 시
    // 발견된 영화와 인기 영화를 가져오는 작업을 수행
    init {
        fetchDiscoverMovies()
    }
    init {
        fetchTrendingMovies()

    }


    // fetchDiscoverMovies() 함수는
    // ViewModel의 초기화 블록에서 호출되어
    // 발견된 영화 데이터를 가져오는 작업을 시작합니다.

    // viewModelScope는 ViewModel의 내부 전용으로 범위를 한정하고
    // 화면이 닫히거나 ViewModel이 더 이상 사용되지 않으면
    // 자동으로 취소되는 코루틴 범위를 제공하며 launch는
    // 새로운 코루틴으로 비동기 작업수행
    private fun fetchDiscoverMovies() = viewModelScope.launch {
        // fetchDiscoverMovie() 메서드는 서버에서 영화데이터 Flow 형태로 반환합니다
        // collectAndHandler 함수는
        // Flow의 상태(로딩, 성공, 오류)를 처리하는 데 사용됩니다.
        repository.fetchDiscoverMovie().collectAndHandler(
            onError = { error ->
                _homeState.update {
                    it.copy(isLoading = false, error = error?.localizedMessage)
                }
            },
            onLoading = {
                _homeState.update {
                    it.copy(isLoading = true, error = null)
                }
            }
        ) { movie ->
            _homeState.update {
                it.copy(isLoading = false, error = null, discoverMovies = movie)
            }
        }
    }
    private fun fetchTrendingMovies() = viewModelScope.launch {
        // fetchTrendingMovies() 메서드는 서버에서 영화데이터 Flow 형태로 반환합니다
        // collectAndHandler 함수는
        // Flow의 상태(로딩, 성공, 오류)를 처리하는 데 사용됩니다.
        repository.fetchTrendingMovie().collectAndHandler(
            onError = { error ->
                _homeState.update {
                    it.copy(isLoading = false, error = error?.localizedMessage)
                }
            },
            onLoading = {
                _homeState.update {
                    it.copy(isLoading = true, error = null)
                }
            }
        ) { movie ->
            _homeState.update {
                it.copy(isLoading = false, error = null, trendingMovies = movie)
            }
        }
    }
}


// ViewModel + State 조합은 안드로이드 앱에서
// UI 상태 관리를 효과적으로 처리하는 패턴입니다.

// HomeState 데이터 클래스는
// 홈 화면의 상태를 나타내는 데 사용됩니다.
// discoverMovies: 발견된 영화 목록을 나타내는 프로퍼티입니다.
// trendingMovies: 인기 영화 목록을 나타내는 프로퍼티입니다.
// error: 오류 메시지를 나타내는 프로퍼티입니다.
data class HomeState(
    val discoverMovies: List<Movie> = emptyList(),
    val trendingMovies: List<Movie> = emptyList(),
    val error: String? = null,
    val isLoading: Boolean = false
)
