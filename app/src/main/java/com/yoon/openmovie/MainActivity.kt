package com.yoon.openmovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.yoon.openmovie.ui.home.HomeScreen
import com.yoon.openmovie.ui.navigation.MovieNavigationGraph
import com.yoon.openmovie.ui.theme.JetMovieTheme
import dagger.hilt.android.AndroidEntryPoint

// @AndroidEntryPoint 어노테이션은 Hilt를 사용하여
// MainActivity에서 의존성 주입을 가능하게 합니다.
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetMovieTheme {
                App()
            }
        }
    }
    // App 컴포저블 함수는 애플리케이션의 주요 UI 구조를 정의합니다.
    @Composable
    fun App(){
        // rememberNavController()는 네비게이션 컨트롤러를 생성하고 기억합니다.
        val navController = rememberNavController()
        // Scaffold는 머티리얼 디자인의 기본 레이아웃 구조를 제공합니다.
        Scaffold(modifier = Modifier.fillMaxSize()) {
            // MovieNavigationGraph는 네비게이션 그래프를 설정하는 커스텀 컴포저블입니다.
            // navController를 전달하고, 패딩을 적용합니다.
            // 이를 통해 앱 내 화면 간의 네비게이션을 관리합니다.
            // 'it'는 Scaffold에서 제공하는 패딩 값을 나타냅니다.
            // Modifier.padding(it)는 이 패딩 값을 MovieNavigationGraph에 적용합니다.
            MovieNavigationGraph(
                navController = navController,
                modifier = Modifier.padding(it)
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetMovieTheme {
        Greeting("Android")
    }
}