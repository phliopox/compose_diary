package com.example.gradientdiary.presentation.ui.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gradientdiary.R
import com.example.gradientdiary.data.storage.getFontResource
import com.example.gradientdiary.presentation.viewModel.SettingViewModel
import timber.log.Timber

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
   // var selectedFont by remember { mutableStateOf("restart") }
    LazyColumn {
        item {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "글꼴 미리보기", style = previewTextStyle)
                Spacer(modifier = Modifier.height(16.dp))
                /*     Spacer(modifier = Modifier.height(16.dp))
                     Button(onClick = { settingViewModel.applyFontSelection() }) {
                         Text(text = "적용")
                     }
                     Spacer(modifier = Modifier.height(16.dp))*/
                Text("오늘도 다양한 기록을 카테고리로 분류해서 보관해요", style = previewTextStyle)
            }
        }
        this@LazyColumn.fontSelectionColumn(onFontSelected = settingViewModel::updateFontSelection)
    }
}



fun LazyListScope.fontSelectionColumn(onFontSelected: (String) -> Unit) {

    items(fontNames) {
        val fontResource = FontFamily(Font(fontResource = getFontResource(it)))
        val isSelected = //it == selectedFont


        Row {
            Text(getFontName(it),fontFamily = fontResource,
                fontSize = 18.sp)
            if (isSelected) {
                Timber.e("icon cleked ")
                Icon(
                    painter = painterResource(id = R.drawable.baseline_check_24),
                    contentDescription = "Selected"
                )
            }
        }
    }
}

fun getFontName(str : String) : String{
    return when(str){
        "restart" -> "나눔 손글씨 다시 시작해체"
        "saeenum" -> "강원교육새움체"
        "ongeul_julison" -> "온글잎 주리 손편지체"
        "ongeul_iplyuttung" -> "온글잎 류뚱체"
        "leeseoyun" -> "이서연체"
        "adultkid" -> "어른아이체"
        else -> "kopub world 돋움체"
    }
}