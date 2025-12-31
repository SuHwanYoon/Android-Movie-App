package com.yoon.openmovie.ui.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.yoon.openmovie.movie_detail.domain.models.Review
import com.yoon.openmovie.ui.components.CollapsibleText
import com.yoon.openmovie.ui.home.itemSpacing
import kotlin.math.round

/**
 * 리뷰 작성자, 작성 날짜, 접을 수 있는 리뷰 내용, 그리고 별점 아이콘이 포함된 평점을 표시하는 단일 리뷰 항목을 구성합니다.
 *
 * 이 Composable은 리뷰 정보를 수직 방향의 열(Column)로 배치합니다.
 * 작성자와 날짜를 결합하여 표시하고, 긴 텍스트 내용은 [CollapsibleText]를 사용하여 처리하며,
 * 평점은 아이콘과 함께 시각적으로 나타냅니다.
 *
 * @param modifier Column 레이아웃에 적용할 수정자(Modifier)입니다.
 * @param review 표시할 데이터(작성자, 날짜, 내용, 평점)를 담고 있는 [Review] 도메인 객체입니다.
 */
@Composable
fun ReviewItem(
    modifier: Modifier = Modifier,
    review: Review
){
    Column(modifier) {
        // 작성자 이름과 작성일시를 포함하는 AnnotatedString 변수 생성
        val nameAnnotatedString: AnnotatedString = buildAnnotatedString {
            append(review.author)
            append(" - ")
            append(review.createdAt)
        }
        // 평점을 굵은 글씨로 표시하는 AnnotatedString 변수 생성
        val ratingAnnotatedString: AnnotatedString = buildAnnotatedString {
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(round(review.rating).toString())
            append(" / 10")
            pop()
        }
        // 리뷰 작성자와 작성일시 일반 본문 스타일로 표시
        Text(
            text = nameAnnotatedString,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )

        // 리뷰 내용을 접힘/펼침 기능이 있는 텍스트로 표시
        Spacer(modifier = Modifier.height(itemSpacing))
        CollapsibleText(text = review.content, style = MaterialTheme.typography.bodyLarge)

        // 평점을 별 아이콘과 함께 표시
        Spacer(modifier = Modifier.height(itemSpacing))
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color.Yellow)
            Text(text = ratingAnnotatedString, style = MaterialTheme.typography.bodySmall)
        }
    }
}