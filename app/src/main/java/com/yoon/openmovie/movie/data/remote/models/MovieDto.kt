package com.yoon.openmovie.movie.data.remote.models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//json to kotlin puglin을 사용하여 json을 복사해서 자동생성
//MovieDto는 API가 보내주는 전체 응답구조를 그대로 받는 컨테이너
//nuallble인 이유는 API응답이 항상 모든 필드를 포함하지 않을수 있기 때문
//@SerialName 어노테이션은 JSON 키와 Kotlin 속성 이름 간의 매핑을 지정하는 데 사용됩니다.
// Serializable과 SerialName을 함께 사용하는 이유는 JSON 데이터의 키 이름이 Kotlin 속성 이름과 다를 수 있기 때문입니다.
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