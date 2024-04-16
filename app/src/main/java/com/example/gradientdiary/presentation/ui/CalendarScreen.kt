package com.example.gradientdiary.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.getBlankCountOfMonth
import com.example.gradientdiary.presentation.getDaysInCurrentMonth
import com.example.gradientdiary.presentation.getMonth
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Paddings
import com.example.gradientdiary.presentation.ui.component.DayBlock

val dayName = listOf("일", "월", "화", "수", "목", "금", "토")

@Composable
fun CalendarScreen(
    paddingValues: PaddingValues,
    handleClickAddDiaryButton: () -> Unit,
    handleClickCalendarColumn: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val availableWidth = screenWidth - 40.dp // star , end padding 값 빼기
    val dayNameWidth = availableWidth / 7

    Column(modifier = Modifier.padding(paddingValues)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "${getMonth()}월의",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(end = Paddings.large)
            )
            Text(
                "일기", style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(end = Paddings.large)
            )
            Icon(
                painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = null
            )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Paddings.extra3),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            dayName.forEach {
                Text(
                    text = it,
                    modifier = Modifier.width(dayNameWidth),
                    style = MaterialTheme.typography.titleMedium.copy(color = DefaultText),
                    textAlign = TextAlign.Center
                )
            }
        }

        CustomCalendarView(
            handleClickCalendarColumn
        )

    }
}

@Composable
fun CustomCalendarView(handleClickCalendarColumn: () -> Unit) {
    val daysInMonth = getDaysInCurrentMonth()
    val blankCount = getBlankCountOfMonth()

    var currentDay = 1


    Column {
        val totalWeeks =
            (daysInMonth + blankCount) / 7 + if ((daysInMonth + blankCount) % 7 != 0) 1 else 0
        repeat(totalWeeks) { rowIndex ->
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                repeat(7) { columnIndex ->
                    val dayIndex = rowIndex * 7 + columnIndex - blankCount
                    if (dayIndex in 0 until daysInMonth) {
                        DayBlock(
                            day = currentDay++
                        ) {
                            handleClickCalendarColumn()
                        }
                    } else {
                        DayBlock(
                            day = 0
                        ) {}
                    }
                }
            }
        }
    }


}