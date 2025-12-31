package com.yoon.openmovie.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle

/**
 * 접힘 및 펼침 상태를 전환할 수 있는 텍스트를 표시하는 Composable입니다.
 *
 * 텍스트가 접힌 상태(collapsed)일 때는 지정된 줄 수만큼만 표시되고 말줄임표(...) 처리됩니다.
 * 텍스트를 클릭하면 전체 내용을 보여주는 펼침 상태(expanded)와 접힌 상태 간 전환이 이루어집니다.
 *
 * 참고: 현재 구현에서는 "...more"/"less" 버튼을 위한 `annotatedString`을 생성하고 있지만,
 * 실제 렌더링되는 `Text` 컴포넌트에는 반영되지 않고 있으며 클릭 시 `maxLines` 속성만 변경됩니다.
 *
 * @param modifier 텍스트 레이아웃에 적용할 [Modifier]입니다.
 * @param text 표시할 문자열 내용입니다.
 * @param collapsedMaxLines 접힌 상태에서 표시할 최대 줄 수입니다. 기본값은 3입니다.
 * @param style 텍스트에 적용할 [TextStyle]입니다. 기본값은 [MaterialTheme.typography.bodyMedium]입니다.
 */
@Composable
fun CollapsibleText(
    modifier: Modifier = Modifier,
    text: String,
    collapsedMaxLines: Int = 3,
    style: TextStyle = MaterialTheme.typography.bodyMedium
){
    // 텍스트의 접힘/펼침 상태를 관리하는 상태 변수
    var isExpanded by remember {
        // 상태주기를 유지하며 초기값은 false로 설정하여 접힌 상태로 시작
        mutableStateOf(false)
    }
    // 접힘/펼침 상태에 따라 표시할 "more"/"less" 텍스트를 포함하는 AnnotatedString 생성
    // AnnotatedString은 스타일이 적용된 텍스트를 표현하는 데 사용
    val annotatedString: AnnotatedString = buildAnnotatedString {
        if (!isExpanded){
            withStyle(SpanStyle(color = Color.Blue)){
                append("...more")
            }
        }else{
            withStyle(SpanStyle(color = Color.Blue)){
                append(" less")
            }
        }
    }
    Column{
        // 실제로 렌더링되는 텍스트 컴포넌트
        Text(
            text = text,
            modifier = Modifier.clickable{isExpanded = !isExpanded},
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLines,
            overflow = TextOverflow.Ellipsis,
            style = style
        )
        //  추가된 AnnotatedString을 표시하는 텍스트 컴포넌트
        // "more"/"less" 텍스트를 클릭하면 접힘/펼침 상태가 전환됨
        Text(
            text = annotatedString,
            modifier = Modifier.clickable{isExpanded = !isExpanded},
            maxLines = if (isExpanded) Int.MAX_VALUE else collapsedMaxLines,
            overflow = TextOverflow.Ellipsis,
            style = style
        )
    }
}