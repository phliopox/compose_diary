package com.example.gradientdiary.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.presentation.getBlankCountOfMonth
import com.example.gradientdiary.presentation.getDaysInCurrentMonth
import com.example.gradientdiary.presentation.getFirstDayNameOfMonth
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.ui.CustomCalendarView

@Composable
fun DayBlock(
    day: Int
) {
    val day1 = if (day != 0 && day < 10) " $day" else if (day >= 10) "$day" else "  "
    Column(
        modifier = Modifier.size(50.dp, 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            //modifier = Modifier.fillMaxSize(),
            text = day1,
            style = MaterialTheme.typography.bodySmall,
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
                )
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