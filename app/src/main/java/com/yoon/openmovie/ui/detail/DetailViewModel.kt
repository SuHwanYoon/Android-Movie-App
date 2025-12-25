package com.yoon.openmovie.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoon.openmovie.movie.domain.models.Movie
import com.yoon.openmovie.movie_detail.domain.models.MovieDetail
import com.yoon.openmovie.movie_detail.domain.repository.MovieDetailRepository
import com.yoon.openmovie.utils.K
import com.yoon.openmovie.utils.collectAndHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * 영화 상세 화면의 비즈니스 로직과 상태 관리를 담당하는 ViewModel입니다.
 *
 * 이 클래스는 영화 상세 정보를 불러오고, 관련 데이터를 관리하며 UI 상태([DetailState])를 업데이트합니다.
 * Hilt를 사용하여 [MovieDetailRepository]와 [SavedStateHandle]을 주입받습니다.
 *
 * @property repository 영화 상세 정보를 가져오기 위한 데이터 레포지토리.
 * @property savedStateHandle 프로세스 재생성 시 데이터를 유지하거나, 네비게이션 인자(Arguments)를 가져오기 위한 핸들러.
 */
@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MovieDetailRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    // MutableStateFlow는 가변 상태 흐름을 나타내며, StateFlow는 읽기 전용 상태 흐름을 나타냅니다.
    // _detailState는 ViewModel 내부에서 상태를 변경하는 용도의 MutableStateFlow로 선언되어 있습니다.
    // detailState는 UI계층이  읽기 전용으로 사용할 목적으로 StateFlow로 제공
    private val _detailState = MutableStateFlow(DetailState())
    val detailState = _detailState.asStateFlow()

    // savedStateHandle을 사용하여 이전 화면에서 전달된 영화 ID를 안전하게 가져옵니다.
    // K.MOVIE_ID 키를 사용하여 영화 ID를 검색하며, 값이 없을 경우 기본값 -1을 할당합니다.
    val id: Int = savedStateHandle.get<Int>(K.MOVIE_ID) ?: -1

    init {
        fetchMovieDetailById()
    }

    /**
     * 저장된 ID를 기반으로 영화 상세 정보를 비동기적으로 가져옵니다.
     *
     * 이 함수는 `viewModelScope` 내에서 실행되며 다음 단계를 수행합니다:
     * 1. `id`가 유효하지 않은 경우(-1), 에러 상태로 업데이트하고 종료합니다.
     * 2. `id`가 유효한 경우, [repository]를 통해 영화 상세 정보를 요청합니다.
     * 3. 요청 상태(로딩, 에러, 성공)에 따라 [_detailState]를 업데이트하여 UI에 반영합니다.
     *
     * - **Loading**: 데이터를 불러오는 동안 `isLoading`을 true로 설정합니다.
     * - **Error**: 요청 실패 시 `isLoading`을 false로 설정하고 에러 메시지를 저장합니다.
     * - **Success**: 데이터 수신 성공 시 `isLoading`을 false로 설정하고 받아온 [MovieDetail] 정보를 저장합니다.
     */
    private fun fetchMovieDetailById() = viewModelScope.launch {
        when (id) {
            -1 -> _detailState.update { it.copy(isLoading = false, error = "Movie not found") }
            else -> repository.fetchMovieDetail(id).collectAndHandler(
                onError = { error ->
                    _detailState.update { it.copy(isLoading = false, error = error?.message) }
                },
                onLoading = {
                    _detailState.update { it.copy(isLoading = true, error = null) }
                }
            ) { movieDetail ->
                _detailState.update {
                    it.copy(
                        isLoading = false,
                        error = null,
                        movieDetail = movieDetail
                    )
                }
            }
        }
    }

     fun fetchMovie() = viewModelScope.launch {
        repository.fetchMovie().collectAndHandler(
            onError = { error ->
                _detailState.update { it.copy(isMovieLoading = false, error = error?.message) }
            },
            onLoading = {
                _detailState.update { it.copy(isMovieLoading = true, error = null) }
            }
        ) { movies ->
            _detailState.update { it.copy(isLoading = false, error = null, movies = movies) }
        }
    }
}


/**
 * 영화 상세 화면의 UI 상태를 나타내는 데이터 클래스입니다.
 *
 * 특정 영화에 대한 상세 정보, 관련 영화 목록, 로딩 상태 및 발생 가능한 에러 메시지 등
 * 화면을 구성하는 데 필요한 모든 데이터를 보유합니다.
 *
 * @property movieDetail 특정 영화의 상세 정보(제목, 줄거리, 개봉일 등)입니다. 데이터가 아직 로드되지 않은 경우 null입니다.
 * @property movies 상세 조회된 영화와 연관된 추천 영화 또는 비슷한 영화 목록입니다. 기본값은 빈 목록입니다.
 * @property isLoading 영화 상세 정보를 불러오는 중인지 여부를 나타냅니다. 네트워크 요청 중이면 true, 아니면 false입니다.
 * @property error 데이터 로딩 중 실패가 발생했을 때 표시할 에러 메시지입니다. 에러가 없으면 null입니다.
 * @property isMovieLoading 연관된 [movies] 목록을 불러오는 중인지 여부를 나타냅니다. 관련 영화 요청 중이면 true, 아니면 false입니다.
 */
data class DetailState(
    val movieDetail: MovieDetail? = null,
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isMovieLoading: Boolean = false
)