package com.example.gradientdiary.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme

@Composable
fun DayBlock(
    modifier: Modifier,
    day: Int
) {
    Column(modifier = modifier) {
        Text(text = day.toString(), style = MaterialTheme.typography.bodySmall)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDayBlock() {
    GradientDiaryTheme {
        Row() {
            repeat(7) {
                DayBlock(
                    Modifier.padding(vertical = 15.dp, horizontal = 20.dp),
                    it + 1
                )
            }
        }
    }
}