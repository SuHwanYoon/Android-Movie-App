package com.yoon.openmovie.common.data


// ApiMapper 인터페이스는 API 데이터 전송 객체(DTO)를 정제하여
// 도메인 모델로 변환하는 매핑 기능을 정의합니다.
// 제네릭 타입 매개변수 Domain과 Entity를 사용하여
// 다양한 도메인 모델과 API DTO 간의 매핑을 지원합니다.
// Moive뿐만 아니라 여러 다른 모델이 있다고해도 <>제네릭타입 매개변수
// 에 맞춰서 재사용 가능
// interface내부에 선언된 메서드는 구현 클래스에서 반드시 구현해야 합니다.
interface ApiMapper<Domain, Entity> {
    // mapToDomain 메서드는 주어진 API DTO(Entity)를
    // 도메인 모델(Domain)로 변환하는 기능을 제공합니다.
    fun mapToDomain(apiDto: Entity): Domain
}