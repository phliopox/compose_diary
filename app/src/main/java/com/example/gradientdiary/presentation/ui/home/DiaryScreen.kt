package com.example.gradientdiary.presentation.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Paddings


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen(
    handleClickAddDiaryButton: (String) -> Unit,
    handleClickCalendarColumn: (String) -> Unit
) {
    val iconSize = Modifier.size(24.dp)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            modifier = iconSize,
                            painter = painterResource(R.drawable.settings_svgrepo_com),
                            contentDescription = " "
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            modifier = iconSize,
                            painter = painterResource(R.drawable.search_svgrepo_com),
                            contentDescription = " "
                        )
                    }
                    IconButton(onClick = { }) {
                        Icon(
                            modifier = iconSize,
                            painter = painterResource(R.drawable.list_svgrepo_com),
                            contentDescription = " "
                        )
                    }
                }
            )

        }
    ) { innerPadding ->
        val paddingValues = PaddingValues(
            start = Paddings.extra,
            top = innerPadding.calculateTopPadding() + Paddings.extra,
            end = Paddings.extra,
            bottom = innerPadding.calculateBottomPadding()
        )

        CalendarScreen(paddingValues,
            handleClickAddDiaryButton,
            handleClickCalendarColumn)

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGradationDiary() {
    GradientDiaryTheme {
        DiaryScreen({}, {})
    }
}