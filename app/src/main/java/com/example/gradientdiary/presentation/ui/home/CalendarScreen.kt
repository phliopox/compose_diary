package com.example.gradientdiary.presentation.ui.home

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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.R
import com.example.gradientdiary.data.storage.SharedPrefsStorageProvider
import com.example.gradientdiary.presentation.getDaysInMonth
import com.example.gradientdiary.presentation.getFirstDayOfWeek
import com.example.gradientdiary.presentation.getMonth
import com.example.gradientdiary.presentation.theme.DefaultText
import com.example.gradientdiary.presentation.theme.Paddings
import com.example.gradientdiary.presentation.ui.component.DayBlock
import timber.log.Timber

val dayName = listOf("일", "월", "화", "수", "목", "금", "토")

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarScreen(
    paddingValues: PaddingValues,
    handleClickAddDiaryButton: () -> Unit,
    handleClickCalendarColumn: (String) -> Unit
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val availableWidth = screenWidth - 40.dp // star , end padding 값 빼기
    val dayNameWidth = availableWidth / 7

    val context = LocalContext.current
    val pref = SharedPrefsStorageProvider(context)
    val category = pref.getCategory() // 현재 선택된 category , 스토리지에 없을시 "일기" 로 반환된다.
    val currentMonth = pref.getCurrentMonth()
    val currentYear = pref.getCurrentYear()

    var year by remember { mutableIntStateOf(currentYear) }
    var month by remember { mutableIntStateOf(currentMonth) }



    Column(modifier = Modifier.padding(paddingValues)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "${month}월",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(end = Paddings.large)
            )
            Text(
                category, style = MaterialTheme.typography.displayMedium,
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
        val pagerState = rememberPagerState(getMonth() - 1,0f) { 12 }
        Column(
            modifier = Modifier.weight(0.6f),
            verticalArrangement = Arrangement.Top
        ) {
            VerticalPager(
                state = pagerState
            ) { page ->
                Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxSize()) {
                    val currentPageMonth = pagerState.currentPage + 1
                    pref.saveCurrentMonth(currentPageMonth)
                    pref.saveCurrentYear(year)
                    // 최근 조회한 월 pref 에 넣어두기

                    CustomCalendarView(
                        year,
                        currentPageMonth,
                        handleClickCalendarColumn
                    )
                    if (pagerState.currentPage != page) {
                        month = currentPageMonth
                    }
                }
            }
        }
        //다이어리 작성 btn
        Column(
            modifier = Modifier
                .weight(0.2f)
                .fillMaxWidth()
                .padding(bottom = Paddings.extra3),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("dddddd")
        }
    }
}

@Composable
fun CustomCalendarView(year: Int, month: Int, handleClickCalendarColumn: (String) -> Unit) {
    val daysInMonth = getDaysInMonth(year, month)
    val firstDayOfWeek = getFirstDayOfWeek(year, month) // 해당 월의 첫째날의 요일을 가져옴
    var blankCount = if (firstDayOfWeek == 7) 0 else firstDayOfWeek
    var currentDay = 1
    Column() {
        val totalWeeks =
            (daysInMonth + blankCount) / 7 + if ((daysInMonth + blankCount) % 7 != 0) 1 else 0

        //Timber.e("month : $month \n dayInMonth : $daysInMonth \n firstDayof : $firstDayOfWeek , blankCount : $blankCount")
        //Timber.e("totalWeeks : $totalWeeks")

        repeat(totalWeeks) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                var count = 0
                repeat(7) {
                    if (blankCount > 0) {
                        // 1일이 일요일이 아닐 경우 그 수만큼 빈 블록을 추가
                        DayBlock(day = 0) {}
                        blankCount--
                        count++
                    } else if (currentDay <= daysInMonth) {
                        // 실제 날짜를 추가
                        DayBlock(day = currentDay++) {clickedDay->
                            val monthString = if(month<10) "0$month" else month
                            handleClickCalendarColumn("$year$monthString$clickedDay")
                        }
                        count++
                    }
                }
                // 남은 요일이 있을 경우 빈 블록을 추가
                repeat(7 - count) {
                    DayBlock(day = 0) {}
                }
            }
        }
    }
}

