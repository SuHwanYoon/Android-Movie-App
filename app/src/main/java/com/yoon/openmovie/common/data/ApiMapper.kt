package com.yoon.openmovie.common.data


/**
 * API 데이터 전송 객체(DTO)를 도메인 모델로 변환하는 매핑 기능을 정의하는 인터페이스입니다.
 *
 * 제네릭 타입 매개변수 [Domain]과 [Entity]를 사용하여 다양한 도메인 모델과 API DTO 간의 매핑을 지원합니다.
 * 이를 통해 Movie 모델뿐만 아니라 다양한 모델에 대해 유연하게 재사용할 수 있습니다.
 *
 * @param Domain 변환될 대상인 도메인 모델의 타입입니다.
 * @param Entity API로부터 전달받은 데이터 전송 객체(DTO)의 타입입니다.
 */
interface ApiMapper<Domain, Entity> {
    // mapToDomain 메서드는 주어진 API DTO(Entity)를
    // 도메인 모델(Domain)로 변환하는 기능을 제공합니다.
    fun mapToDomain(apiDto: Entity): Domain
}