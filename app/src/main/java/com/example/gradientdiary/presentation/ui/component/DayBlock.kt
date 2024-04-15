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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Paddings
import com.example.gradientdiary.presentation.ui.CustomCalendarView

@Composable
fun DayBlock(
    day: Int,
    dayClick :(Int)-> Unit
) {
    val dayText = if (day != 0 && day < 10) " $day" else if (day >= 10) "$day" else "  "
    Column(
        modifier = Modifier
            .size(Paddings.xxextra, Paddings.xxextra7)
            .clickable {
                if(day>0) {
                    dayClick(day)
                }
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
        Row() {
            repeat(7) {
                DayBlock(
                    it
                ){

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCustomCalendarView() {
    GradientDiaryTheme {
        CustomCalendarView()
    }
}