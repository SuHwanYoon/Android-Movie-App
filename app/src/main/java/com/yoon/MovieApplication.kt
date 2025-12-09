package com.yoon

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// MovieApplication 클래스는 Hilt를 사용하여
// 의존성 주입을 설정하는 애플리케이션 클래스입니다.
// @HiltAndroidApp 어노테이션은
// Hilt가 이 애플리케이션 클래스에서 의존성 주입을 처리하도록 지정합니다.
// Application 클래스는 앱 전체에서 한 번만 생성되며,
// 앱이 시작될때 안드로이드 시스템에 의해 호출되며
// 전역 상태관리, 의존성 초기화, 라이브러리 초기화 등에 사용됨
@HiltAndroidApp
class MovieApplication : Application() {
}