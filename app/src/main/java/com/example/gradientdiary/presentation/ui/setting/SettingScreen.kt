package com.example.gradientdiary.presentation.ui.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter.State.Empty.painter
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.Dimens
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme

/*
* 설정 메뉴
* 메인 홈 바꾸기 -> 구조적 수정 불가피
* 글꼴 바꾸기
* 알림 설정하기
* 언어 설정
* 앱 평가하기
* */
@Composable
fun SettingScreen(
    handleAlertSetting: () -> Unit,
    handleTextStyleSetting: () -> Unit,
    handleLanguageSetting: () -> Unit,
    handleReviewIntent : ()->Unit
) {

    val interactionSource = remember { MutableInteractionSource() }
    val iconModifier = Modifier
        .size(40.dp)
        .padding(horizontal = 8.dp, vertical = 8.dp)
    Column(
        Modifier
            .fillMaxWidth()
            .padding(Dimens.dp20)
    ) {
        /* Row(
             modifier = Modifier
                 .fillMaxWidth()
                 .clickable(
                     interactionSource = interactionSource,
                     indication = null,
                 ) {
                     mainHomeIconId=
                     when(mainHomeIconId){
                         R.drawable.ic_calendar -> R.drawable.list_svgrepo_com
                         else -> R.drawable.ic_calendar
                     }
                 },
             verticalAlignment = Alignment.CenterVertically,
             horizontalArrangement = Arrangement.SpaceBetween
         ) {
             Text("메인화면 설정")

             Icon(
                 modifier = iconModifier,
                 painter = painterResource(id = mainHomeIconId),
                 contentDescription = "mainhome"
             )

         }*/
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                ) {
                    handleTextStyleSetting()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("글자 설정")

            Icon(
                modifier = iconModifier,
                painter = painterResource(id = R.drawable.ic_text),
                contentDescription = "text_style"
            )
        }

    }
}

@Preview()
@Composable
fun SettingScreenPreview() {
    GradientDiaryTheme {
        SettingScreen({},{},{},{})
    }
}