package com.yoon.openmovie.ui.home.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.yoon.openmovie.ui.home.itemSpacing

// MovieCard 컴포저블 함수는 영화 정보를 표시하는 카드 UI 구성 요소입니다.
// modifier 매개변수는 UI 요소의 레이아웃과 스타일을 조정하는 데 사용됩니다.
// shapes 매개변수는 카드의 모서리 모양을 정의합니다.
// bgColor 매개변수는 카드의 배경 색상을 정의합니다.
// content 매개변수는 카드 내부에 표시할 내용을 정의하는 컴포저블 람다 함수입니다.
@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    shapes: CornerBasedShape = MaterialTheme.shapes.large,
    bgColor : Color = Color.Black.copy(.8f),
    content : @Composable () -> Unit,
) {
    Card(
        shape = shapes,
        colors = CardDefaults.cardColors(
            containerColor = bgColor,
            contentColor = Color.White
        ),
        modifier = modifier,
    ){
        content()
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewMovieCard(){
    MovieCard{
        Text(text = "Action" , modifier = Modifier.padding(itemSpacing))
    }
}