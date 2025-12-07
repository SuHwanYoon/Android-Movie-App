package com.yoon.openmovie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//@Serializable 어노테이션은 Kotlin 데이터 클래스를 JSON과 같은 형식으로 직렬화하고 역직렬화하는 데 사용됩니다.
//직렬화는 객체를 JSON 문자열로 변환하는 과정이고, 역직렬화는 JSON 문자열을 객체로 변환하는 과정입니다.1
//@SerialName 어노테이션은 JSON 키와 Kotlin 속성 이름 간의 매핑을 지정하는 데 사용됩니다.
// Serializable과 SerialName을 함께 사용하는 이유는 JSON 데이터의 키 이름이 Kotlin 속성 이름과 다를 수 있기 때문입니다.
// MovieDto 데이터 전송 객체는 영화 목록 API 응답을 나타냅니다.
// 페이지 번호, 영화 결과 목록, 총 페이지 수 및 총 결과 수를 포함합니다.
// 각 속성은 JSON 키와 매핑되며, null 가능성을 처리합니다.
@Serializable
data class MovieDto(
    @SerialName("page")
    val page: Int? = null,
    @SerialName("results")
    val results: List<Result?>? = null,
    @SerialName("total_pages")
    val totalPages: Int? = null,
    @SerialName("total_results")
    val totalResults: Int? = null
)