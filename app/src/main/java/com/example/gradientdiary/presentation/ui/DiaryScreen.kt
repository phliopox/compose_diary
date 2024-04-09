package com.example.gradientdiary.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.getMonth
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /*TODO*/ },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_view_day_24),
                            contentDescription = " "
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_view_day_24),
                            contentDescription = " "
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_view_day_24),
                            contentDescription = " "
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_view_day_24),
                            contentDescription = " "
                        )
                    }
                }

            )

        }
    ) { innerPadding ->
        val paddingValues = PaddingValues(
            start = 20.dp,
            top = innerPadding.calculateTopPadding()+20.dp,
            end = 20.dp,
            bottom = innerPadding.calculateBottomPadding()
        )

        CalendarScreen(paddingValues)

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGradationDiary() {
    GradientDiaryTheme {
        DiaryScreen()
    }
}