package com.example.gradientdiary.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Dimens
import com.example.gradientdiary.presentation.ui.home.CustomCalendarView

@Composable
fun DayBlock(
    day: Int,
    dayClick: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val availableWidth = screenWidth - 40.dp // star , end padding 값 빼기
    val dayNameWidth = availableWidth / 7

    val dayText = if (day != 0 && day < 10) " $day" else if (day >= 10) "$day" else "  "
    Column(
        modifier = Modifier
            .size(dayNameWidth, Dimens.dp70)
            .clickable(day > 0) {
                val dayString = if(day<10){ "0$day" }else day.toString()
                dayClick(dayString)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = dayText,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDayBlock() {
    GradientDiaryTheme {
        DayBlock(
            1
        ) {

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCustomCalendarView() {
    GradientDiaryTheme {
        CustomCalendarView(2024, 4, {})
    }
}