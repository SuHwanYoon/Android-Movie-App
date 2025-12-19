package com.yoon.openmovie.movie_detail.data.mapper_impl

import com.yoon.openmovie.common.data.ApiMapper
import com.yoon.openmovie.movie_detail.data.remote.models.CastDto
import com.yoon.openmovie.movie_detail.data.remote.models.MovieDetailDto
import com.yoon.openmovie.movie_detail.domain.models.Cast
import com.yoon.openmovie.movie_detail.domain.models.MovieDetail
import com.yoon.openmovie.movie_detail.domain.models.Review
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * 영화 상세 정보 데이터 변환을 담당하는 [ApiMapper]의 구현체입니다.
 *
 * 이 클래스는 API 계층의 데이터 모델인 [MovieDetailDto]를 도메인 계층에서 사용하는
 * [MovieDetail] 모델로 매핑하는 역할을 수행합니다.
 *
 * 주요 기능 및 특징:
 * - **Null 및 빈 값 처리:** `formatEmptyValue` 등의 헬퍼 함수를 사용하여 API 응답의 null 또는 빈 값을 안전한 기본값(예: "Unknown title")으로 대체합니다.
 * - **데이터 포맷팅:**
 *   - 날짜 및 타임스탬프를 사용자가 읽기 쉬운 형식으로 변환합니다 (`formatTimeStamp`).
 *   - 영화 상영 시간(분 단위)을 "시간 분" 형식으로 변환합니다 (`convertMinutesToHours`).
 *   - 출연진(Cast), 리뷰(Reviews), 장르(Genres) 등의 중첩된 리스트 데이터를 도메인 모델 리스트로 변환합니다.
 * - **UI 안전성 보장:** 뷰 계층에서 데이터를 바로 사용할 수 있도록 가공하여 앱의 안정성을 높입니다.
 */
class MovieDetailMapperImpl: ApiMapper<MovieDetail , MovieDetailDto> {
    /**
     * API 응답 객체([MovieDetailDto])를 도메인 모델([MovieDetail])로 변환합니다.
     *
     * 이 함수는 [MovieDetailDto]의 nullable 필드들을 안전하게 처리하여 비즈니스 로직에서 사용할 수 있는
     * [MovieDetail] 객체를 생성합니다. 주요 변환 로직은 다음과 같습니다:
     *
     * - **Null 안전성 처리:** 대부분의 문자열 필드는 [formatEmptyValue]를 통해 null이거나 빈 값일 경우 "Unknown [Category]" 형식의 기본값으로 대체됩니다.
     * - **숫자 처리:** ID, 인기도, 평점 등의 숫자 필드가 null일 경우 0 또는 0.0으로 초기화합니다.
     * - **컬렉션 매핑:** 장르, 출연진(Cast), 제작 국가, 리뷰 등의 리스트 데이터는 각각의 내부 매핑 로직을 거쳐 도메인 모델 리스트로 변환되며, 원본이 null일 경우 빈 리스트를 반환합니다.
     * - **시간 형식 변환:** 상영 시간(runtime)은 분 단위 정수에서 "Xh Ym" 문자열 포맷으로 변환됩니다.
     * - **리뷰 데이터:** 리뷰 내 작성 시간은 ISO 8601 형식에서 "yy-MM-dd" 형식으로 재포맷팅됩니다.
     *
     * @param apiDto 네트워크 계층에서 전달받은 영화 상세 정보 DTO 객체입니다.
     * @return 뷰나 비즈니스 로직에서 사용하기 적합하게 가공된 [MovieDetail] 도메인 객체를 반환합니다.
     */
    override fun mapToDomain(apiDto: MovieDetailDto): MovieDetail {
        return MovieDetail(
            backdropPath = formatEmptyValue(apiDto.backdropPath),
            genreIds = apiDto.genres?.map { formatEmptyValue(it?.name) } ?: emptyList(),
            id = apiDto.id ?: 0,
            originalLanguage = formatEmptyValue(apiDto.originalLanguage, "language"),
            originalTitle = formatEmptyValue(apiDto.originalTitle, "title"),
            overview = formatEmptyValue(apiDto.overview, "overview"),
            popularity = apiDto.popularity ?: 0.0,
            posterPath = formatEmptyValue(apiDto.posterPath),
            releaseDate = formatEmptyValue(apiDto.releaseDate, "date"),
            title = formatEmptyValue(apiDto.title, "title"),
            voteAverage = apiDto.voteAverage ?: 0.0,
            voteCount = apiDto.voteCount ?: 0,
            video = apiDto.video ?: false,
            cast = formatCast(apiDto.credits?.cast),
            language = apiDto.spokenLanguages?.map { formatEmptyValue(it?.englishName) }
                ?: emptyList(),
            productionCountry = apiDto.productionCountries?.map { formatEmptyValue(it?.name) }
                ?: emptyList(),
            reviews = apiDto.reviews?.results?.map {
                Review(
                    author = formatEmptyValue(it?.author),
                    content = formatEmptyValue(it?.content),
                    createdAt = formatTimeStamp(time = it?.createdAt ?: "0"),
                    id = formatEmptyValue(it?.id),
                    rating = it?.authorDetails?.rating ?: 0.0
                )
            } ?: emptyList(),
            runTime = convertMinutesToHours(apiDto.runtime ?: 0)
        )

    }


    // override 한 mapToDomain에서 사용할 함수 정의


    /**
     * ISO 8601 형식(yyyy-MM-dd'T'HH:mm:ss.SSS'Z')의 타임스탬프 문자열을 지정된 패턴으로 변환합니다.
     *
     * 이 함수는 입력된 [time] 문자열을 API 표준 응답 형식에 맞춰 US 로케일로 파싱합니다.
     * 그 후 기기의 기본 로케일(Locale.getDefault())을 사용하여 [pattern]에 지정된 형식으로 재구성합니다.
     *
     * 파싱에 실패할 경우(예: [time] 형식이 맞지 않는 경우), 원본 [time] 문자열을 그대로 반환합니다.
     *
     * @param pattern 변환할 날짜 형식 패턴 (예: "yy-MM-dd"). 기본값은 "yy-MM-dd"입니다.
     * @param time 변환할 입력 시간 문자열로, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" 형식을 따라야 합니다.
     * @return 포맷팅된 날짜 문자열을 반환하며, 파싱 실패 시 원본 [time] 문자열을 반환합니다.
     */
    private fun formatTimeStamp(pattern: String = "yy-MM-dd", time: String ): String {
        //  inputDateFormatter는 ISO 8601 형식의 날짜 문자열을 파싱하기 위한 포맷터입니다.
        //  Locale.US를 사용하여 고정된 형식으로 파싱합니다.
        val inputDateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        // outputDateFormatter는 기기의 기본 로케일을 사용하여 지정된 패턴으로 날짜를 포맷팅합니다.
        val outputDateFormatter = SimpleDateFormat(
            pattern,
            Locale.getDefault()
        )
        // 입력된 time 문자열을 파싱하여 Date 객체로 변환합니다.
        val date = inputDateFormatter.parse(time)
        // date가 null이 아니면 outputDateFormatter를 사용하여 포맷팅하고, null이면 원본 time 문자열을 반환합니다.
        val formattedDate = date?.let { outputDateFormatter.format(it) } ?: time

        return formattedDate

    }

    /**
     * 분(minute) 단위의 정수 값을 '시간'과 '분' 형식의 문자열로 변환합니다.
     *
     * 입력받은 [minutes] 값을 계산하여 "X시간 Y분" 또는 "Xh Ym" 등의 포맷으로 반환합니다.
     * (예: 150분이 입력되면 "2시간 30분" 반환)
     *
     * @param minutes 변환할 총 분(minute) 단위의 시간입니다.
     * @return 시간과 분으로 변환된 포맷팅된 문자열을 반환합니다.
     */
    private fun convertMinutesToHours(minutes: Int): String{
        val hours = minutes / 60
        val remainingMinutes = minutes % 60
        return "${hours}h ${remainingMinutes}m"
    }

    /**
     * 주어진 문자열 [value]가 null이거나 비어있는지 확인하고, 그렇다면 기본 대체 문자열을 반환합니다.
     *
     * 이 함수는 UI 컴포넌트나 도메인 모델에 빈 값이나 null 값이 전달되는 것을 방지하기 위해 사용됩니다.
     * 입력된 [value]가 유효한 데이터를 포함하고 있다면 그대로 반환합니다.
     *
     * @param value 검사할 문자열 (Nullable).
     * @return [value]가 유효한 경우 그 값을 반환하고, null이거나 빈 문자열인 경우 대체 문자열(예: "-")을 반환합니다.
     */
    private fun formatEmptyValue(value: String?, default: String = ""): String{
        // value가 null이거나 비어있으면 "Unknown $default" 반환
        if (value.isNullOrEmpty()){
            return  "Unknown $default"
        }

        return value
    }

    /**
     * [CastDto] 리스트를 도메인 모델인 [Cast] 리스트로 변환합니다.
     *
     * 이 함수는 API 응답([castDto])이 null인 경우 빈 리스트를 반환하여 안전하게 처리합니다.
     * 각 항목에 대해 다음과 같은 변환 로직을 수행합니다:
     * - **성별(Gender):** 값이 2인 경우 "Actor", 그 외의 경우 "Actress"로 매핑합니다.
     * - **값 포맷팅:** 이름(name)이나 배역(character) 정보가 없거나 비어있는 경우 [formatEmptyValue]를 통해 대체 텍스트("Unknown Name" 등)를 제공합니다.
     * - **ID 및 프로필:** ID가 없을 경우 0으로 초기화하고, 프로필 경로는 그대로 전달합니다.
     *
     * @param castDto API로부터 전달받은 출연진 정보 리스트 (Nullable). 각 항목도 Nullable일 수 있습니다.
     * @return 변환된 [Cast] 객체의 리스트를 반환합니다. 입력이 null이면 빈 리스트를 반환합니다.
     */
    private fun formatCast(castDto: List<CastDto?>?) : List<Cast>{
        return castDto?.map {
            val genderRole = if (it?.gender == 2) "Actor" else "Actress"
            Cast(
                id = it?.id?: 0,
                name = formatEmptyValue(it?.name, "Name"),
                genderRole = genderRole,
                character = formatEmptyValue(it?.character, "Character"),
                profilePath = it?.profilePath
            )
        }?: emptyList()
    }

}