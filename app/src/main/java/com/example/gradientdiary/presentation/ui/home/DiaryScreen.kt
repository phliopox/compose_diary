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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gradientdiary.R
import com.example.gradientdiary.presentation.theme.GradientDiaryTheme
import com.example.gradientdiary.presentation.theme.Dimens
import com.example.gradientdiary.presentation.viewModel.CategoryViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryScreen(
    categoryViewModel: CategoryViewModel,
    handleClickAddDiaryButton: (String) -> Unit,
    handleClickCalendarColumn: (String) -> Unit,
    handleSearchIconClick: () -> Unit,
    handleListViewClick :()->Unit,
    handleSettingClick : ()->Unit
) {
    val iconSize = Modifier.size(24.dp)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    IconButton(onClick = {
                        handleSearchIconClick()
                    }) {
                        Icon(
                            modifier = iconSize,
                            painter = painterResource(R.drawable.search_svgrepo_com),
                            contentDescription = stringResource(id = R.string.search)
                        )
                    }
                    IconButton(onClick = {
                        handleListViewClick()
                    }) {
                        Icon(
                            modifier = iconSize,
                            painter = painterResource(R.drawable.list_svgrepo_com),
                            contentDescription = stringResource(id = R.string.list_view)
                        )
                    }
                    IconButton(onClick = {
                        handleSettingClick()
                    }) {
                        Icon(
                            modifier = iconSize,
                            painter = painterResource(R.drawable.settings_svgrepo_com),
                            contentDescription = stringResource(id = R.string.setting_name)
                        )
                    }
                }
            )

        }
    ) { innerPadding ->
        val paddingValues = PaddingValues(
            start = Dimens.dp20,
            top = innerPadding.calculateTopPadding() + Dimens.dp20,
            end = Dimens.dp20,
            bottom = innerPadding.calculateBottomPadding()
        )

        CalendarScreen(
            categoryViewModel,
            paddingValues,
            handleClickAddDiaryButton,
            handleClickCalendarColumn
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewGradationDiary() {
    GradientDiaryTheme {
        val iconSize = Modifier.size(24.dp)
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = {

                        }) {
                            Icon(
                                modifier = iconSize,
                                painter = painterResource(R.drawable.settings_svgrepo_com),
                                contentDescription = " "
                            )
                        }
                    }
                    ,
                    actions = {
                        IconButton(onClick = {

                        }) {
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
          PaddingValues(
                start = Dimens.dp20,
                top = innerPadding.calculateTopPadding() + Dimens.dp20,
                end = Dimens.dp20,
                bottom = innerPadding.calculateBottomPadding()
            )

        }
    }
}