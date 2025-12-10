package com.yoon.openmovie.utils

import android.util.Log
import kotlinx.coroutines.flow.Flow

// Ext는 Flow<Response<T>>를 다룰때 매번 반복되는 처리 (로딩/성공/실패를)
// 한번에 처리할수 있도록 만든 확장함수
// Flow<Response<T>>타입의 객체를 사용하면 이 메세드를 사용가능
// 즉 ViewModel에서 collect할때 마다 똑같이 try/catch , error 처리, loading처리
// 성공 처리를 쓰지 않도록 하기 위해 만듬
// Flow<Response<T>> 타입에 대한 사용자 정의 확장 함수 collectAndHandler를 선언합니다.
suspend fun <T> Flow<Response<T>>.collectAndHandler(
    // onError 파라미터는 오류가 발생했을 때 호출되는 람다 함수입니다.
    onError: (Throwable?)
    -> Unit = { Log.e("collectAndHandler", "collectAndHandler: error", it) },

    // onLoading 파라미터는 로딩 상태일 때 호출되는 람다 함수입니다.
    onLoading: () -> Unit = { /* Default no-op */ },

    //stateReducer 파라미터는 성공 상태일 때 호출되는 람다 함수입니다.
    stateReducer: (T) -> Unit,
) {
    // collect는 FLow 인터페이스가 가지고 있는 abstract 함수로,
    // Flow에서 방출되는 값을 수집하는 역할을 합니다.
    collect { response ->
        when (response) {
            is Response.Success -> {
                // 성공 상태일 때 stateReducer 함수를 호출하여 데이터를 처리합니다.
                stateReducer(response.data)
            }

            is Response.Error -> {
                // 오류 상태일 때 onError 함수를 호출하여 오류를 처리합니다.
                onError(response.error)
            }

            is Response.Loading -> {
                // 로딩 상태일 때 onLoading 함수를 호출합니다.
                onLoading()
            }
        }
    }
}