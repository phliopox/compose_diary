package com.example.gradientdiary.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.gradientdiary.presentation.getDaysInMonth
import com.example.gradientdiary.presentation.getMonth
import com.example.gradientdiary.presentation.getYear
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Paddings
import com.example.gradientdiary.presentation.ui.component.DayBlock
import timber.log.Timber

val dayName = listOf("일", "월", "화", "수", "목", "금", "토")

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(
    paddingValues: PaddingValues,
    handleClickAddDiaryButton: () -> Unit,
    handleClickCalendarColumn: () -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val availableWidth = screenWidth - 40.dp // star , end padding 값 빼기
    val dayNameWidth = availableWidth / 7
    var year by remember { mutableStateOf(getYear()) }
    var month by remember { mutableStateOf(getMonth()) }

    Column(modifier = Modifier.padding(paddingValues)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "${month}월",
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
        // Display 10 items
        val pagerState = rememberPagerState(getMonth()-1)
        VerticalPager(
            modifier = Modifier.fillMaxSize(),
            pageCount = 12,
            state = pagerState
        ) { page ->
            val currentMonth = pagerState.currentPage+1
            CustomCalendarView(
                year,
                currentMonth,
                handleClickCalendarColumn
            )
            if (pagerState.currentPage != page) {
                month = currentMonth
            }
        }
    }
}

@Composable
fun CustomCalendarView(year: Int, month: Int, handleClickCalendarColumn: () -> Unit) {
    val daysInMonth = getDaysInMonth(year, month)
    val blankCount = getBlankCountOfMonth(year, month)

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