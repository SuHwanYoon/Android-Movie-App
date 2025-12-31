package com.yoon.openmovie.ui.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.yoon.openmovie.R
import com.yoon.openmovie.movie_detail.domain.models.Cast
import com.yoon.openmovie.utils.K

/**
 * 단일 배우의 정보를 표시하는 Composable 함수입니다.
 * 프로필 이미지, 성별/역할, 그리고 성과 이름을 수직으로 배치하여 보여줍니다.
 *
 * 이 컴포넌트는 다음과 같은 요소로 구성됩니다:
 * 1. 주어진 URL 경로에서 비동기적으로 로드되는 원형 프로필 이미지.
 * 2. 배우의 성별 또는 역할 (작은 텍스트).
 * 3. 배우의 이름과 성 (굵은 텍스트).
 *
 * @param modifier 이 아이템의 레이아웃에 적용할 수정자(Modifier). 기본값은 [Modifier]입니다.
 * @param cast 배우의 데이터(프로필 경로, 성별/역할, 이름, 성)를 포함하는 [Cast] 객체.
 */
@Composable
fun ActorItem(
    modifier: Modifier = Modifier,
    cast: Cast
) {
    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data("${K.BASE_IMAGE_URL}${cast.profilePath}")
        .crossfade(true)
        .build()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 배우 프로필 이미지를 비동기적으로 로드하고 표시
        // modifier로 크기와 모양을 지정
        // ContentScale.Crop을 사용하여 이미지를 잘라내어 표시
        AsyncImage(
            model =  imageRequest,
            contentDescription = null,
            modifier = Modifier.size(48.dp).clip(CircleShape),
            contentScale = ContentScale.Crop,
            onError = { it.result.throwable.printStackTrace() },
            placeholder = painterResource(id = R.drawable.baseline_person_24)
        )
        // 배우의 이미지 아래에 성별 역할과 이름을 텍스트로 표시
        Text(text = cast.genderRole, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = cast.firstName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = cast.lastName,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}