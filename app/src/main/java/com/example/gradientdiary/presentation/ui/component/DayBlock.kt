package com.example.gradientdiary.presentation.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.gradientdiary.presentation.theme.Paddings
import com.example.gradientdiary.presentation.ui.home.CustomCalendarView

@Composable
fun DayBlock(
    day: Int,
    dayClick: (Int) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val availableWidth = screenWidth - 40.dp // star , end padding 값 빼기
    val dayNameWidth = availableWidth / 7
//day != 0 &&
    val dayText = if ( day < 10) " $day" else if (day >= 10) "$day" else "  "
    Column(
        modifier = Modifier
            .size(dayNameWidth, Paddings.xxextra7)
            .clickable(day > 0) {
                dayClick(day)
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