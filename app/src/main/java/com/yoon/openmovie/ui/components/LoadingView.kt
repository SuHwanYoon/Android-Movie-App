package com.yoon.openmovie.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// LoadingView 컴포저블 함수는 로딩 상태를 표시하는 UI 구성 요소입니다.
// modifier 매개변수는 UI 요소의 레이아웃과 스타일을 조정하는 데 사용됩니다.
// isLoading 매개변수는 로딩 상태를 나타내는 불리언 값입니다.
@Composable
fun LoadingView(modifier: Modifier = Modifier, isLoading: Boolean) {
    // AnimatedVisibility 컴포저블을 사용하여 isLoading 값에 따라
    // 로딩 인디케이터의 표시 여부를 애니메이션과 함께 제어
    // enter 매개변수는 로딩 인디케이터가 나타날 때 적용할 애니메이션 효과를 정의
    // fadeIn()는 서서히 나타나는 효과를 적용
    // expandVertically()는 세로 방향으로 확장되는 효과를 적용
    AnimatedVisibility(
        isLoading,
        enter = fadeIn() + expandVertically()
    ) {
        // Box 레이아웃을 사용하여 로딩 인디케이터를 화면 중앙에 배치
        // fillMaxSize()는 Box가 가능한 최대 크기를 차지하도록 설정
        // contentAlignment = Alignment.Center로 내부 콘텐츠를 중앙에 정렬
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // CircularProgressIndicator 컴포저블을 사용하여
            // 회전하는 원형 로딩 인디케이터를 화면 중앙에 표시
            CircularProgressIndicator()
        }
    }
}