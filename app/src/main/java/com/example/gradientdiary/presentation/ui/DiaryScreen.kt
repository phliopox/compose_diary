package com.example.gradientdiary.presentation.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Paddings


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
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
            start = Paddings.extra,
            top = innerPadding.calculateTopPadding()+Paddings.extra,
            end = Paddings.extra,
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