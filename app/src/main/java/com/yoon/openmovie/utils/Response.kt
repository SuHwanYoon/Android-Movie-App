package com.yoon.openmovie.utils
//비동기 작업, 특히 API 호출의 다양한 상태 (성공, 오류, 로딩)를 처리하기 위한
// sealed class Response를 정의하여 데이터 로딩 결과에 대한 구조화된 관리를 가능하게 합니다.
// T는 제네릭 타입으로, 어떤 데이터 타입이든 사용할 수 있습니다.
// sealed는 이 클래스를 상속하는 클래스들이 같은 파일 내에 정의되어야 함을 의미합니다.
sealed class Response<T>{
    class Success<T>(val data:T): Response<T>()
    class Error<T>(val error:Throwable?,val data:T? = null): Response<T>()
    class Loading<T>: Response<T>()
}