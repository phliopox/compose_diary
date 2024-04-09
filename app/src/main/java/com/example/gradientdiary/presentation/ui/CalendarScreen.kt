package com.example.gradientdiary.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.getDaysInCurrentMonth
import com.example.gradientdiary.presentation.getFirstDayNameOfMonth
import com.example.gradientdiary.presentation.getMonth

 val dayName = listOf("일","월","화","수","목","금","토")
@Composable
fun CalendarScreen(paddingValues : PaddingValues) {
    Column(modifier = Modifier.padding(paddingValues)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                "${getMonth()}월의",
                style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(end = 10.dp)
            )
            Text("일기", style = MaterialTheme.typography.displayMedium,
                modifier = Modifier.padding(end = 10.dp)
            )
            Icon(
                painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
                contentDescription = null
            )
        }
        Row {
            dayName.forEach { Text(text = it, style = MaterialTheme.typography.titleMedium) }
        }
        val addBlank = getFirstDayNameOfMonth()
        val day = getDaysInCurrentMonth()

        repeat(getDaysInCurrentMonth()){

        }
    }
}