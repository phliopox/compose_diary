package com.example.gradientdiary.presentation.ui.setting

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gradientdiary.R
import com.example.gradientdiary.data.storage.getFontResource
import com.example.gradientdiary.presentation.theme.Dimens
import com.example.gradientdiary.presentation.theme.Grey70
import com.example.gradientdiary.presentation.viewModel.SettingViewModel

val fontNames = listOf(
    "restart",
    "saeenum",
    "ongeul_julison",
    "ongeul_iplyuttung",
    "leeseoyun",
    "adultkid",
    "kopub_worlddotum"
)

@Composable
fun TextStyleSettingScreen(
    settingViewModel: SettingViewModel
) {


    val previewTextStyle by settingViewModel.previewTextStyle.collectAsState()

    val selectedFont by settingViewModel.selectedFont.collectAsState()
    LazyColumn {
        item {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(Dimens.dp20)
                    .clip(RoundedCornerShape(Dimens.dp8))
                    .border(Dimens.dp1, Grey70, RoundedCornerShape(Dimens.dp8))
                    .padding(Dimens.dp30),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "카테고리로 분류하여 기록할 수 있는 다이어리,\n${stringResource(id = R.string.app_name)} 입니다 !\n글꼴을 선택하여 스타일을 미리 확인해보세요 ^^",
                    style = previewTextStyle,
                    fontSize = 20.sp
                )
            }
        }
        this@LazyColumn.fontSelectionColumn(
            selectedFont,
            onFontSelected = settingViewModel::updateFontSelection
        )
    }
}


fun LazyListScope.fontSelectionColumn(selectedFont: String, onFontSelected: (String) -> Unit) {

    item{
        Spacer(modifier = Modifier.height(Dimens.dp20))
        Divider(
            color = Grey70,
            modifier = Modifier
                .height(Dimens.dp1)
                .padding(horizontal = Dimens.dp20)
                .fillMaxWidth()
        )
    }
    items(fontNames) {
        val fontResource = FontFamily(Font(getFontResource(it)))
        val isSelected = it == selectedFont
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.dp20)
                .clickable(
                    MutableInteractionSource(),
                    null
                ) {
                    onFontSelected(it)
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                getFontName(it), fontFamily = fontResource,
                fontSize = 18.sp
            )
            RadioButton(
                selected = isSelected,
                onClick = { onFontSelected(it) },
                colors = RadioButtonDefaults.colors(selectedColor = Color.Black)
            )
        }
        Divider(
            color = Grey70,
            modifier = Modifier
                .height(1.dp)
                .padding(horizontal = Dimens.dp20)
                .fillMaxWidth()
        )
    }
}

fun getFontName(str: String): String {
    return when (str) {
        "restart" -> "나눔 손글씨 다시 시작해체"
        "saeenum" -> "강원교육새움체"
        "ongeul_julison" -> "온글잎 주리 손편지체"
        "ongeul_iplyuttung" -> "온글잎 류뚱체"
        "leeseoyun" -> "이서연체"
        "adultkid" -> "어른아이체"
        else -> "kopub world 돋움체"
    }
}